package com.radik.my.project.controllers;

import com.radik.my.project.entity.User;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/")
    String home() {
        return "login";
    }


    @GetMapping("/registration")
    String registration() {
        return "registration";
    }

    @PostMapping("/register")
    @ResponseBody
    ResponseEntity<Void> userRegister(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();

        userService.create(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/success-registration")
    String successRegister() {
        return "success-registration";
    }

    @GetMapping("/profile")
    String profile(Principal principal, Model model) {
        UserDto dto = UserMapper.toUserDto(userService.get(principal.getName()).get());

        model.addAttribute("user", dto);
        return "profile";
    }

}
