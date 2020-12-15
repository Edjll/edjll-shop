package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Role;
import ru.edjll.shop.model.User;
import ru.edjll.shop.repository.UserRepository;

import java.util.Collections;
import java.util.Date;

@Service
public class RegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private EmailService emailService;

    public ru.edjll.shop.domain.User save(User user) {
        ru.edjll.shop.domain.User userDomain = new ru.edjll.shop.domain.User();

        userDomain.setFirstName(user.getFirstName());
        userDomain.setLastName(user.getLastName());
        userDomain.setPatronymic(user.getPatronymic());
        userDomain.setEmail(user.getEmail());
        userDomain.setPhone(user.getPhone());
        userDomain.setRegistrationDate(new Date());
        userDomain.setCity(cityService.getCity(user.getCity()));

        userDomain.setPassword(passwordEncoder.encode(user.getPassword()));
        userDomain.setRoles(Collections.singleton(Role.USER));

        emailService.getAll().forEach(email -> {
            if (email.getEmail().equals(user.getEmail())) {
                emailService.delete(email);
            }
        });

        return userRepository.save(userDomain);
    }
}
