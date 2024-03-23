package com.radik.my.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class GuestController {

    @GetMapping("/contact")
    String contacts() {
        return "contact";
    }

    @GetMapping("/restaurants")
    String restaurants() {
        return "restaurants";
    }

    @GetMapping("/restaurant")
    String restaurant() {
        return "restaurant";
    }

}
