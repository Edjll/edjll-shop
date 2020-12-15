package ru.edjll.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.edjll.shop.domain.User;

@Controller
public class LoginController {

    @GetMapping("/user/login")
    public String getLoginPage(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "redirect:/admin";
        }

        return"main/user/login";
    }


}
