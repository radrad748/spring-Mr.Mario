package com.radik.my.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @PostMapping("/add")
    ResponseEntity<String> commentAdd(Principal principal, @RequestBody Map<String, String> requestBody) {
        String comment = requestBody.get("comment");
        String title = requestBody.get("title");
        String userEmail = principal.getName();

        if (comment == null || comment.trim().isEmpty() || comment.length() > 500)
            return new ResponseEntity<>("Некорректные данные", HttpStatus.CONFLICT);



        return ResponseEntity.ok().build();
    }

}
