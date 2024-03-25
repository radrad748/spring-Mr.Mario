package com.radik.my.project.controllers;

import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.RestaurantName;
import com.radik.my.project.entity.menu.Menu;
import com.radik.my.project.entity.menu.TypeMenu;
import com.radik.my.project.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class GuestController {

    private final RestaurantService resService;

    @GetMapping("/contact")
    String contacts() {
        return "contact";
    }

    @GetMapping("/restaurants")
    String restaurants() {
        return "restaurants";
    }

    @GetMapping("/restaurant/the-lot")
    String restaurant(Model model) {
        Restaurant res = resService.getRestaurant(RestaurantName.THE_LOT.getValue());
        model.addAttribute("menu", res.getMenu());
        model.addAttribute("title", res.getTitle());

        return "restaurant-the-lot";
    }

}
