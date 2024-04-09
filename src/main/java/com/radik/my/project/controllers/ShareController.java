package com.radik.my.project.controllers;

import com.radik.my.project.entity.Share;
import com.radik.my.project.services.ShareService;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.ShareMapper;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.ShareDto;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shares")
public class ShareController {

    private final ShareService shareService;
    private final UserService userService;

    @GetMapping
    String pageShares(Model model, HttpSession session, Principal principal) {
        List<ShareDto> shares = ShareMapper.toListShareDto(shareService.findAllDesc());
        if (principal != null) addUserDtoToSession(session, principal.getName());

        model.addAttribute("shares", shares);
        return "shares";
    }

    /* --------------------------------------------------------------------------------- */

    private void addUserDtoToSession(HttpSession session, String email) {
        UserDto dto = (UserDto) session.getAttribute("user");

        if (dto == null) {
            dto = UserMapper.toUserDto(userService.get(email));
            session.setAttribute("user", dto);
        }
    }
}
