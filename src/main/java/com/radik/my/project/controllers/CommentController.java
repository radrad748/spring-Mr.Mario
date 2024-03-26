package com.radik.my.project.controllers;

import com.radik.my.project.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commService;

    @PostMapping("/add")
    ResponseEntity<String> commentAdd(Principal principal, @RequestBody Map<String, String> requestBody) {
        String comment = requestBody.get("comment");
        String title = requestBody.get("title");
        String userEmail = principal.getName();

        if (Objects.isNull(comment) || comment.trim().isEmpty() || comment.length() > 500)
            return new ResponseEntity<>("Некорректные данные", HttpStatus.CONFLICT);

        if (Objects.isNull(title) || title.trim().isEmpty() || Objects.isNull(userEmail) || userEmail.trim().isEmpty())
            return ResponseEntity.badRequest().build();

        commService.saveComment(comment, userEmail, title);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (Objects.isNull(id) || id <= 0) return ResponseEntity.badRequest().build();

        commService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
