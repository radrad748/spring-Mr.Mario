package com.radik.my.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/")
    String home() {
        return "login";
    }


    @GetMapping("/registration")
    String registration() {
        return "registration";
    }

}
