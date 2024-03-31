package com.radik.my.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radik.my.project.entity.*;
import com.radik.my.project.services.OrderService;
import com.radik.my.project.services.RestaurantService;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.MenuMapper;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final RestaurantService resService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/preparation")
    String orderPage(HttpSession session, Model model) {
        List<OrderModel> orderList = (List<OrderModel>) session.getAttribute("orderList");
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        UserDto userDto = (UserDto) session.getAttribute("user");
        String orderJson = parseOrderToJson(orderList);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderList", orderList);
        model.addAttribute("user", userDto);
        model.addAttribute("orderListJson", orderJson);

        return "order-preparation";
    }

    @PostMapping("/preparation")
    ResponseEntity<String> orderCount(@RequestBody Map<String, String> requestBody, HttpServletRequest request, Principal principal) {
        HttpSession session = dtoInSession(principal.getName(), request);
        if (requestBody.isEmpty()) return ResponseEntity.badRequest().build();

        String restaurantTitle = requestBody.get("restaurantTitle");
        if (Objects.isNull(restaurantTitle) || restaurantTitle.trim().isEmpty()) return ResponseEntity.badRequest().build();

        requestBody.remove("restaurantTitle");
        Restaurant res = resService.getRestaurant(restaurantTitle);

        if (res == null) return ResponseEntity.badRequest().build();

        List<OrderModel> orderList = getClientOrder(res, requestBody);
        BigDecimal totalPrice = totalPriceOrder(orderList);

        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("orderList", orderList);
        session.setAttribute("restaurant", res);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    ResponseEntity<String> order(@RequestBody Map<String, String> requestBody, HttpSession session) {
        Restaurant res = (Restaurant) session.getAttribute("restaurant");
        String totalPrice = requestBody.get("totalPrice");
        requestBody.remove("totalPrice");
        BigDecimal price = new BigDecimal(totalPrice);
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userService.get(userDto.getEmail());

        orderService.saveOrder(res, user, price, requestBody);

        BigDecimal newUserCount = userDto.getCount().subtract(price);
        userDto.setCount(newUserCount);
        session.setAttribute("user", userDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/success-order")
    String successOrder() {
        return "success-order";
    }


    private HttpSession dtoInSession(String email, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserDto dto = (UserDto) session.getAttribute("user");
        if (dto == null) {
            dto = UserMapper.toUserDto(userService.get(email));
            session.setAttribute("user", dto);
        }
        return session;
    }

    private List<OrderModel> getClientOrder(Restaurant res, Map<String, String> requestBody) {
        List<OrderModel> result = new ArrayList<>();

        for (Map.Entry<String, String> entry : requestBody.entrySet()) {
            long id = Long.parseLong(entry.getKey());
            int count = Integer.parseInt(entry.getValue());
            for (Menu menu : res.getMenu()) {
                if (menu.getId() == id) {
                    result.add(new OrderModel(MenuMapper.toMenuDto(menu), count));
                    break;
                }
            }
        }
        return result;
    }

    private BigDecimal totalPriceOrder(List<OrderModel> totalOrder) {
        BigDecimal totalPrice = new BigDecimal("00.00");

        for (OrderModel order : totalOrder) {
            BigDecimal price = order.getMenu().getPrice();
            BigDecimal count = new BigDecimal(order.getCount());
            BigDecimal multiplier = price.multiply(count);
            totalPrice = totalPrice.add(multiplier);
        }

        return totalPrice;
    }

    private String parseOrderToJson(List<OrderModel> orderList) {
        ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            result = mapper.writeValueAsString(orderList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
