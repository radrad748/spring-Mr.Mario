package com.radik.my.project.controllers;

import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

        model.addAttribute("users", users);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("pageSizeOptions", pageSizeOptions);

        return "admin";
    }

    /* ----------------------------------------------------------------------------------- */

    private int[] createPageSizeOptions(int ... size) {
        int[] pageSizeOptions = new int[size.length];

        for (int i = 0; i < size.length; i++) {
            pageSizeOptions[i] = size[i];
        }

        return pageSizeOptions;
    }

}
