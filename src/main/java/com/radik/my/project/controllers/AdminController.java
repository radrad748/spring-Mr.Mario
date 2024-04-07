package com.radik.my.project.controllers;

import com.radik.my.project.entity.enums.Role;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    String admin(@RequestParam(name = "page", defaultValue = "0") int page,
                 @RequestParam(name = "size", defaultValue = "3") int size, Model model) {
        if (page < 0) page = 0;
        if (size < 3) size = 3;

        long totalPages = userService.getCount();
        if (totalPages > size) totalPages = (long) Math.ceil((double) totalPages / size);
        if (page > totalPages - 1) page = (int) totalPages - 1;

        List<UserDto> users = userService.findAll(page, size);
        int[] pageSizeOptions = createPageSizeOptions(3, 5, 10);

        List<String> rolesOption = getListRoles(Role.values());

        model.addAttribute("users", users);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("pageSizeOptions", pageSizeOptions);
        model.addAttribute("rolesOption", rolesOption);

        return "admin";
    }

    @DeleteMapping("/delete/user/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (Objects.isNull(id)) return ResponseEntity.notFound().build();

        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bOrU}/user/{id}")
    ResponseEntity<Void> blockUser(@PathVariable String bOrU, @PathVariable Long id) {
        if (Objects.isNull(id) || Objects.isNull(bOrU) || bOrU.trim().isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.ifExistId(id)) return ResponseEntity.badRequest().build();

        if (bOrU.equals("block")) {
            userService.block(id);
            return ResponseEntity.ok().build();
        } else if (bOrU.equals("unblock")) {
            userService.unblock(id);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }



    /* ----------------------------------------------------------------------------------- */

    private int[] createPageSizeOptions(int ... size) {
        int[] pageSizeOptions = new int[size.length];

        for (int i = 0; i < size.length; i++) {
            pageSizeOptions[i] = size[i];
        }

        return pageSizeOptions;
    }

    private List<String> getListRoles(Role[] roles) {
        List<String> result = new ArrayList<>();

        for (Role role : roles) {
            if (role == Role.USER) break;
            result.add(role.toString());
        }

        return result;
    }

}
