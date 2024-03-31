package com.radik.my.project.services;

import com.radik.my.project.entity.*;
import com.radik.my.project.repositories.OrderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final UserService userService;

    @Transactional
    public void saveOrder(Restaurant res, User user, BigDecimal sum, Map<String, String> menu) {
        Order order = Order.builder()
                .restaurant(res)
                .user(user)
                .sum(sum)
                .delivery(true)
                .build();

        addListCountMenuOrderToOrder(order, menu, res.getMenu());
        orderDao.save(order);

        BigDecimal newUserCount = user.getCount().subtract(sum);
        user.setCount(newUserCount);
        userService.update(user);
    }

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




}
