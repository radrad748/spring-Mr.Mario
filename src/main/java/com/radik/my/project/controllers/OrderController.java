package com.radik.my.project.controllers;

import com.radik.my.project.services.RestaurantService;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final RestaurantService resService;
    private final UserService userService;

    @GetMapping("/preparation")
    String orderPage() {
        return "order-preparation";
    }

    @PostMapping("/preparation")
    ResponseEntity<String> orderCount(@RequestBody Map<String, String> requestBody, HttpServletRequest request, Principal principal) {
        HttpSession session = dtoInSession(principal.getName(), request);
        if (requestBody.isEmpty()) return ResponseEntity.badRequest().build();

        String restaurantName = requestBody.get("restaurantName");
        requestBody.remove("restaurantName");


        return ResponseEntity.ok().build();
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

}
