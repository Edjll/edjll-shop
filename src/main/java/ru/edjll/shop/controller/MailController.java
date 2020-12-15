package ru.edjll.shop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/admin/mail/create")
    public String getCreateMailPage() {
        return "admin/mail/create";
    }
}
