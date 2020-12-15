package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.User;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    public void updateAuthenticationToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User authorizedUser = (User) auth.getPrincipal();
        User userDomain = userService.getUserById(authorizedUser.getId());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDomain, auth.getCredentials(), auth.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
