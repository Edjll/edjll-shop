package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.User;
import ru.edjll.shop.model.UserPassword;
import ru.edjll.shop.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/profile/update")
    public ResponseEntity updateUser(
            @RequestPart(name = "user") @Validated User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> errors = new HashMap<>();
        errors.put("user.password", "");;
        errors.put("error", "Неверный пароль");

        if (userService.updateUser(user)) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/profile/password/update")
    public ResponseEntity changePassword(
            @RequestPart(name = "user") UserPassword userPassword
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("user.password", "");;
        errors.put("error", "Неверный пароль");

        if (userService.changePassword(userPassword)) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/user/get/only")
    public Page<User> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "") String searchParam,
            @RequestParam(required = false, defaultValue = "2") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userService.getAllOnlyUsersModelPage(pageable, searchParam);
    }

    @GetMapping("/user/login/fail")
    public ResponseEntity<Map<String, String>> loginError() {
        Map<String, String> errors = new HashMap<>();
        errors.put("user.email", "");
        errors.put("user.password", "");;
        errors.put("error", "Неверный логин или пароль");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }
}
