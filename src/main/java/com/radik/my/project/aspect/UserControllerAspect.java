package com.radik.my.project.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class UserControllerAspect {

    @Before("execution(* com.radik.my.project.controllers.UserController.*(..)) && args(principal, periodOrders,  model, request)")
    void validMethodProfile(Principal principal, @RequestParam("period") String periodOrders, Model model, HttpServletRequest request) {
        if (periodOrders.trim().isEmpty()) throw new RuntimeException("Отсутствует параметр период времени заказов");

        Set<String> setPeriod = new HashSet<>();
        setPeriod.add("week");
        setPeriod.add("month");
        setPeriod.add("year");
        setPeriod.add("all");

        if(!setPeriod.contains(periodOrders)) throw new RuntimeException("Неверный параметр период времени заказов");
    }
}
