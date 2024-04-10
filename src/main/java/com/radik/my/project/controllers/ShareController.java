package com.radik.my.project.controllers;

import com.radik.my.project.entity.Share;
import com.radik.my.project.services.OrderService;
import com.radik.my.project.services.ShareService;
import com.radik.my.project.services.UserService;
import com.radik.my.project.utils.Mappers.ShareMapper;
import com.radik.my.project.utils.Mappers.UserMapper;
import com.radik.my.project.utils.dto.ShareDto;
import com.radik.my.project.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shares")
public class ShareController {

    private final ShareService shareService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    String pageShares(Model model, HttpSession session, Principal principal) {
        List<ShareDto> shares = ShareMapper.toListShareDto(shareService.findAllDesc());
        if (principal != null) {
            UserDto dto = addUserDtoToSession(session, principal.getName());
            model.addAttribute("user", dto);
        }

        model.addAttribute("shares", shares);
        return "shares";
    }

    @PostMapping("/order/{shareId}")
    ResponseEntity<String> makeOrderShare(@PathVariable Long shareId, HttpSession session, @RequestBody Map<String, String> requestBody) {
        if (requestBody.isEmpty()) return ResponseEntity.badRequest().build();

        UserDto userDto = (UserDto) session.getAttribute("user");
        String address = requestBody.get("address");
        String phone = requestBody.get("phone");
        BigDecimal sum = new BigDecimal(requestBody.get("allPrice"));

        int compareCount = userDto.getCount().compareTo(sum);
        if (compareCount < 0) return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);

        if (!validDate(address, phone)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        orderService.saveShareOrder(shareId, userDto.getEmail(), sum, address, phone);
        return ResponseEntity.ok().build();
    }

    /* --------------------------------------------------------------------------------- */

    private UserDto addUserDtoToSession(HttpSession session, String email) {
        UserDto dto = (UserDto) session.getAttribute("user");

        if (dto == null) {
            dto = UserMapper.toUserDto(userService.get(email));
            session.setAttribute("user", dto);
            return dto;
        }

        return dto;
    }

    private boolean  validDate(String address, String phone) {
        if (Objects.isNull(address) || Objects.isNull(phone) ||
        address.trim().isEmpty() || phone.trim().isEmpty() ||
        address.length() < 6 || address.length() > 100 || phone.length() < 6 || phone.length() > 20)
            return false;

        String regexPhone = "^\\+?[0-9]+$";
        return phone.matches(regexPhone);
    }
}
