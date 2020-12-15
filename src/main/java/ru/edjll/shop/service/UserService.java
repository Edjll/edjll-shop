package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.UserPassword;
import ru.edjll.shop.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private QuestionService questionService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public Page<User> getPageUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Object> getRegistrationUsersByDates(LocalDate dateStart, LocalDate dateEnd) {
        return userRepository.getRegistrationUsersByDates(dateStart, dateEnd);
    }

    public boolean updateUser(ru.edjll.shop.model.User user) {
        ru.edjll.shop.domain.User userDomain = userRepository.getOne(user.getId());

        if (passwordEncoder.matches(user.getPassword(), userDomain.getPassword())) {
            userDomain.setFirstName(user.getFirstName());
            userDomain.setLastName(user.getLastName());
            userDomain.setPatronymic(user.getPatronymic());
            userDomain.setEmail(user.getEmail());
            userDomain.setPhone(user.getPhone());
            userDomain.setCity(cityService.getCity(user.getCity()));
            userRepository.save(userDomain);
            securityService.updateAuthenticationToken();
            return true;
        }
        return false;
    }

    public boolean changePassword(UserPassword userPassword) {
        User user = userRepository.getOne(userPassword.getId());

        if (passwordEncoder.matches(userPassword.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userPassword.getNewPassword()));
            userRepository.save(user);
            securityService.updateAuthenticationToken();
            return true;
        }
        return false;
    }

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    public Page<ru.edjll.shop.model.User> getAllOnlyUsersModelPage(Pageable pageable, String searchParam) {
        if (searchParam.isEmpty()) {
            return userRepository.getAllByEmployeeIsNull(pageable).map(ru.edjll.shop.model.User::new);
        } else {
            return userRepository.getAllByEmployeeIsNullAndEmailContainsIgnoreCase(pageable, searchParam).map(ru.edjll.shop.model.User::new);
        }
    }

    public Page<User> getAllOnlyUsersPage(Pageable pageable) {
        return userRepository.getAllByEmployeeIsNull(pageable);
    }

    public List<User> getAllOnlyUser() {
        return userRepository.getAllOnlyUser();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
