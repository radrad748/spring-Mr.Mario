package com.radik.my.project.controllers;

import com.radik.my.project.entity.Comment;
import com.radik.my.project.entity.Restaurant;
import com.radik.my.project.entity.enums.RestaurantName;
import com.radik.my.project.services.CommentService;
import com.radik.my.project.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class GuestController {

    private final RestaurantService resService;
    private final CommentService commService;
    @GetMapping("/contact")
    String contacts() {
        return "contact";
    }

    @GetMapping("/restaurants")
    String restaurants() {
        return "restaurants";
    }

    @GetMapping("/restaurant/the-lot")
    String restaurant(Model model, @RequestParam int size) {
        Restaurant res = resService.getRestaurant(RestaurantName.THE_LOT.getValue());
        List<Comment> commentList = commService.getCommentsDesc(size);
        long commentCount = commService.countToRestaurant(res);

        model.addAttribute("menu", res.getMenu());
        model.addAttribute("title", res.getTitle());
        model.addAttribute("comments", commentList);
        model.addAttribute("size", size);
        model.addAttribute("count", commentCount);

        return "restaurant-the-lot";
    }


}
