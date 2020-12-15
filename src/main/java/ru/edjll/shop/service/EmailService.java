package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Email;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.repository.EmailRepository;

import java.util.List;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserService userService;

    public void saveEmail(String email) {
        Email emailOld = emailRepository.getByEmail(email);
        User user = userService.getUserByEmail(email);
        if (user == null && emailOld == null) {
            Email emailDomain = new Email();
            emailDomain.setEmail(email);
            emailRepository.save(emailDomain);
        }
    }

    public List<Email> getAll() {
        return emailRepository.findAll();
    }

    public void delete(Email email) {
        emailRepository.delete(email);
    }
}
