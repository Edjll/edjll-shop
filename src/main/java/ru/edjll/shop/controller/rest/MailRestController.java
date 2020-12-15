package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.domain.Email;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.MailMessage;
import ru.edjll.shop.service.EmailService;
import ru.edjll.shop.service.MailSender;
import ru.edjll.shop.service.UserService;

import java.util.List;

@RestController
public class MailRestController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @PostMapping("/admin/mail/send")
    public ResponseEntity<Object> sendMail(@RequestPart("mail") MailMessage mailMessage) {

        List<User> users = userService.getAllOnlyUser();

        List<Email> emails = emailService.getAll();

        mailMessage.setBody("<td colspan='3'>" + mailMessage.getBody() + "</td>");

        users.forEach(user -> {
            mailSender.send(user.getEmail(), mailMessage.getTitle(), mailMessage);
        });

        emails.forEach(email -> {
            mailSender.send(email.getEmail(), mailMessage.getTitle(), mailMessage);
        });

        return ResponseEntity.ok().body(null);
    }
}
