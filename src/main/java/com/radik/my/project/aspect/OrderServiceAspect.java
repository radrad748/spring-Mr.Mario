package com.radik.my.project.aspect;

import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.User;
import com.radik.my.project.utils.exceptions.NotCorrectDataServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class OrderServiceAspect {

    @Before("execution(* com.radik.my.project.services.OrderService.saveOrder(..)) && args(res, user,  sum, address, phone, menu)")
    public void validateArgsMethodSaveOrder(Restaurant res, User user, BigDecimal sum, String address, String phone, Map<String, String> menu) {
        if (Objects.isNull(res) || Objects.isNull(user) || Objects.isNull(sum) || Objects.isNull(address)
        || Objects.isNull(phone) || Objects.isNull(menu)) throw new NotCorrectDataServices("Входные данные не могут быть null");

        if (sum.compareTo(BigDecimal.ZERO) <= 0) throw new NotCorrectDataServices("BigDecimal sum: сумма покупки не должно быть ровна <=0");
    }

}
