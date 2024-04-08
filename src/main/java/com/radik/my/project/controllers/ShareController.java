package com.radik.my.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shares")
public class ShareController {

    @GetMapping
    String pageShares() {
        return "shares";
    }

}
