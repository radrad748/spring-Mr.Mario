package com.radik.my.project.services;

import com.radik.my.project.entity.*;
import com.radik.my.project.entity.enums.PeriodOrders;
import com.radik.my.project.repositories.OrderDao;
import com.radik.my.project.repositories.ShareDao;
import com.radik.my.project.utils.Mappers.OrderMapper;
import com.radik.my.project.utils.dto.OrderDto;
import com.radik.my.project.utils.exceptions.NotCorrectDataServices;
import com.radik.my.project.utils.exceptions.NotCorrectUserDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final ShareDao shareDao;

    @Transactional
    public void saveOrder(Restaurant res, User user, BigDecimal sum, String address, String phone, Map<String, String> menu) {
        Order order = Order.builder()
                .restaurant(res)
                .user(user)
                .sum(sum)
                .address(address)
                .phone(phone)
                .delivery(true)
                .build();

        addListCountMenuOrderToOrder(order, menu, res.getMenu());
        orderDao.save(order);

        BigDecimal newUserCount = user.getCount().subtract(sum);
        user.setCount(newUserCount);
        userService.update(user);
    }

    public List<OrderDto> getOrdersDto(User user, PeriodOrders period) {
        if (Objects.isNull(user) || Objects.isNull(period)) throw new NotCorrectUserDetailsException("Входные данные не могут быть null");

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = getStartDateForUserOrders(endDate, period);

        List<Order> orders = orderDao.getOrders(user, startDate, endDate);

        if (orders.isEmpty()) return new ArrayList<>();

        return OrderMapper.getListOrdersDto(orders);
    }

    @Transactional
    public void saveShareOrder(Long shareId, String userEmail, BigDecimal sum, String address, String phone) {
        Share share = getValidShare(shareId);
        User user = getValidUser(userEmail);

        Order order = Order.builder()
                .restaurant(share.getRestaurant())
                .user(user)
                .sum(sum)
                .address(address)
                .phone(phone)
                .delivery(true)
                .build();

        addListCountMenuOrderToOrder(order, share);
        orderDao.save(order);

        BigDecimal newUserCount = user.getCount().subtract(sum);
        user.setCount(newUserCount);
        userService.update(user);
    }

    /* --------------------------------------------------------------------------------------------------- */

    private void addListCountMenuOrderToOrder(Order order, Map<String, String> mapMenu, List<Menu> resMenu) {
        List<CountMenuOrder> listCmo = new ArrayList<>();

        for (Map.Entry<String, String> entry : mapMenu.entrySet()) {
            int idMenu = Integer.parseInt(entry.getKey());
            int count = Integer.parseInt(entry.getValue());

            for (Menu menu : resMenu) {
                if (menu.getId() == idMenu) {
                    CountMenuOrder cmo = new CountMenuOrder();
                    cmo.setCount(count);
                    cmo.setMenu(menu);
                    cmo.setOrder(order);

                    listCmo.add(cmo);
                    break;
                }
            }
        }
        order.setCountMo(listCmo);
    }

    private void addListCountMenuOrderToOrder(Order order, Share share) {
        List<CountMenuOrder> listCmo = new ArrayList<>();

        for (CountMenuShare cms : share.getCountMList()) {
            CountMenuOrder cmo = new CountMenuOrder();
            cmo.setCount(cms.getCount());
            cmo.setMenu(cms.getMenu());
            cmo.setOrder(order);
            listCmo.add(cmo);
        }

        order.setCountMo(listCmo);
    }


    private LocalDateTime getStartDateForUserOrders(LocalDateTime endDate, PeriodOrders period) {
        switch (period) {
            case WEEK:
                return endDate.minusWeeks(1);
            case MONTH:
                return endDate.minusMonths(1);
            case YEAR:
                return endDate.minusYears(1);
            case ALL_THE_TIME:
                return LocalDateTime.of(2024, 1, 1, 0, 0, 0);
            default:
                return null;
        }
    }

    private Share getValidShare(Long shareId) {
        Share share = shareDao.get(shareId);
        if (share == null) throw new NotCorrectDataServices("Акции по такой id не существует");

        return share;
    }
    private User getValidUser(String email) {
        User user = userService.get(email);
        if (user == null) throw new NotCorrectDataServices("User с таким email не существует");

        return user;
    }

}
