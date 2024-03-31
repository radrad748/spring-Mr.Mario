package com.radik.my.project.aspect;

import com.radik.my.project.entity.Order;
import com.radik.my.project.utils.dto.UserDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class OrderControllerAspect {

    @Around("execution(* com.radik.my.project.controllers.OrderController.*(..)) && args(requestBody, session)")
    public ResponseEntity<String> checkOrderRequestBody(ProceedingJoinPoint joinPoint, Map<String, String> requestBody, HttpSession session) throws Throwable {
        String totalPrice = requestBody.get("totalPrice");
        requestBody.remove("totalPrice");
        if (Objects.isNull(totalPrice) || totalPrice.trim().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            if (Double.parseDouble(totalPrice) <= 0){
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        UserDto userDto = (UserDto) session.getAttribute("user");

        int compare = userDto.getCount().compareTo(new BigDecimal(totalPrice));
        if (compare < 0) return new ResponseEntity<>("Недостаточно средств", HttpStatus.PAYMENT_REQUIRED);

        return (ResponseEntity<String>) joinPoint.proceed();
    }

}
