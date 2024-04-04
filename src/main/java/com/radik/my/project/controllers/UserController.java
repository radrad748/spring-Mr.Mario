package com.radik.my.project.controllers;

import com.radik.my.project.entity.Order;
import com.radik.my.project.entity.User;
import com.radik.my.project.entity.enums.PeriodOrders;
import com.radik.my.project.repositories.CodeAnswer;
import com.radik.my.project.services.OrderService;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.OrderDto;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;


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

        if (userService.ifExistEmail(user.getEmail()))
            return new ResponseEntity<>("пользователь с таким email уже существует", HttpStatus.CONFLICT);

        userService.create(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/success-registration")
    String successRegister() {
        return "success-registration";
    }

    @GetMapping("/profile")
    String profile(Principal principal, @RequestParam("period") String periodOrders, Model model, HttpServletRequest request) {
        PeriodOrders period = getPeriod(periodOrders);
        HttpSession session = request.getSession(true);
        UserDto dto = getUserDto(principal, session);
        model.addAttribute("user", dto);

        List<OrderDto> orders = orderService.getOrdersDto(userService.get(dto.getId()), period);
        model.addAttribute("listOrders", orders);

        return "profile";
    }

    @PostMapping("/account/top-up/{id}")
    String topUp(@PathVariable Long id, @RequestParam int count, Model model, HttpServletRequest request) {
        User user = userService.addCount(id, count);
        UserDto dto = UserMapper.toUserDto(user);

        HttpSession session = request.getSession(false);
        if (session != null) session.setAttribute("user", dto);

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
    ResponseEntity<Void> modifyName(@PathVariable Long id, @RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String name = requestBody.get("name");
        if (!validateName(name)) return ResponseEntity.badRequest().build();

        userService.modifyName(id, name);
        modifySessionUserName(request.getSession(false), name);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/surname/{id}")
    ResponseEntity<Void> modifySurname(@PathVariable Long id, @RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String surname = requestBody.get("surname");
        if (!validateName(surname)) return ResponseEntity.badRequest().build();

        userService.modifySurname(id, surname);
        modifySessionUserSurname(request.getSession(false), surname);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/email/{id}")
    ResponseEntity<String> modifyEmail(@PathVariable Long id, @RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String email = requestBody.get("email");

        if (!validateEmail(email)) return new ResponseEntity<>("Не корректный емейл", HttpStatus.CONFLICT);
        if (userService.ifExistEmail(email))
            return new ResponseEntity<>("пользователь с таким email уже существует", HttpStatus.CONFLICT);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        userService.modifyEmail(id, email);

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        HttpSession session = request.getSession(false);
        modifySessionUserEmail(session, email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/password/{id}")
    ResponseEntity<String> modifyPassword(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        String newPassword = requestBody.get("newPassword");

        String checkValidatePassword = validatePassword(password, newPassword);
        if (!checkValidatePassword.equals("ok"))
            return new ResponseEntity<>(checkValidatePassword, HttpStatus.CONFLICT);

        CodeAnswer code = userService.modifyPassword(id, password, newPassword);
        if (code == CodeAnswer.WRONG_PASSWORD)
            return new ResponseEntity<>("Введен неверный пароль", HttpStatus.CONFLICT);

        return ResponseEntity.ok().build();
    }


    /* ----------------------------------------------------------------------------------------------------- */


    private String validatePassword(String password, String newPassword) {
        if (Objects.isNull(password) || Objects.isNull(newPassword) || password.trim().isEmpty() || newPassword.trim().isEmpty())
            return "Введены некорректные данные";

        if ((password.length() < 6) || (newPassword.length() < 6) || (password.length() > 50) || (newPassword.length() > 50)) {
            return "Пароль должен иметь длину от 6 до 50 символов";
        }

        Pattern digitPattern = Pattern.compile(".*\\d.*");
        Matcher digitMatcherPassword = digitPattern.matcher(password);
        Matcher digitMatcherNewPassword = digitPattern.matcher(newPassword);
        if (!digitMatcherPassword.matches() || !digitMatcherNewPassword.matches()) {
            return "Пароль должен содержать цифры";
        }

        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Matcher upperCaseMatcherPassword = upperCasePattern.matcher(password);
        Matcher upperCaseMatcherNewPassword = upperCasePattern.matcher(newPassword);
        if (!upperCaseMatcherPassword.matches() || !upperCaseMatcherNewPassword.matches()) {
            return "Пароль должен содержать символы верхнего регистра";
        }

        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Matcher lowerCaseMatcherPassword = lowerCasePattern.matcher(password);
        Matcher lowerCaseMatcherNewPassword = lowerCasePattern.matcher(newPassword);
        if (!lowerCaseMatcherPassword.matches() || !lowerCaseMatcherNewPassword.matches()) {
            return "Пароль должен содержать символы нижнего регистра";
        }

        return "ok";
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

    private UserDto getUserDto(Principal principal, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");

        if (userDto == null) {
            userDto = UserMapper.toUserDto(userService.get(principal.getName()));
            session.setAttribute("user", userDto);
            return userDto;
        }

        return userDto;
    }

    private void modifySessionUserEmail(HttpSession session, String email) {
        if (session == null) return;

        UserDto dto = (UserDto) session.getAttribute("user");
        if(dto != null) {
            dto.setEmail(email);
            session.setAttribute("user", dto);
        }
    }
    private void modifySessionUserName(HttpSession session, String name) {
        if (session == null) return;

        UserDto dto = (UserDto) session.getAttribute("user");
        if(dto != null) {
            dto.setFirstName(name);
            session.setAttribute("user", dto);
        }
    }
    private void modifySessionUserSurname(HttpSession session, String surname) {
        if (session == null) return;

        UserDto dto = (UserDto) session.getAttribute("user");
        if(dto != null) {
            dto.setLastName(surname);
            session.setAttribute("user", dto);
        }
    }

    private PeriodOrders getPeriod(String period) {
        for (PeriodOrders p : PeriodOrders.values()) {
            if (p.getValue().equals(period)) return p;
        }
        return null;
    }


}
