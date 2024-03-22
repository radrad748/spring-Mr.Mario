package com.radik.my.project.controllers;

import com.radik.my.project.entity.User;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    ResponseEntity<String> userRegister(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();

        if (userService.ifExistEmail(user.getEmail())) return new ResponseEntity<>("пользователь с таким email уже существует", HttpStatus.CONFLICT);

        userService.create(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/success-registration")
    String successRegister() {
        return "success-registration";
    }

    @GetMapping("/profile")
    String profile(Principal principal, Model model) {
        UserDto dto = UserMapper.toUserDto(userService.get(principal.getName()));
        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping ("/account/top-up/{id}")
    String topUp(@PathVariable Long id, @RequestParam int count, Model model) {
        User user = userService.addCount(id, count);
        UserDto dto = UserMapper.toUserDto(user);
        model.addAttribute("user", dto);
        return "profile";
    }

    @DeleteMapping("/delete/{id}")
    String userDelete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        userService.delete(id);

        return "redirect:/";
    }

    @PostMapping("/modify/name/{id}")
    ResponseEntity<Void> modifyName(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (!validateName(name)) return ResponseEntity.badRequest().build();

        userService.modifyName(id, name);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/modify/surname/{id}")
    ResponseEntity<Void> modifySurname(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String surname = requestBody.get("surname");
        if (!validateName(surname)) return ResponseEntity.badRequest().build();

        userService.modifySurname(id, surname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("modify/email/{id}")
    ResponseEntity<String> modifyEmail(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        System.out.println(validateEmail(email));
        if (!validateEmail(email)) return new ResponseEntity<>("Не корректный емейл", HttpStatus.CONFLICT);
        if (userService.ifExistEmail(email)) return new ResponseEntity<>("пользователь с таким email уже существует", HttpStatus.CONFLICT);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        userService.modifyEmail(id, email);

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return ResponseEntity.ok().build();
    }

    private boolean validateName(String name) {
        if (name.trim().isEmpty()) return false;
        return name.length() >= 3 && name.length() <= 50;
    }

    private boolean validateEmail(String email) {
        String emailPattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


}
