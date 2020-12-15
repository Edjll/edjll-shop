package ru.edjll.shop.validation.user.email;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.User;
import ru.edjll.shop.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    @Autowired
    private UserService userService;


    @Override
    public boolean isValid(User user, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.User userDomain = userService.getUserByEmail(user.getEmail());
        if (userDomain == null || userDomain.getId().equals(user.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.user.email.unique")
                .addPropertyNode("email")
                .addConstraintViolation();
        return false;
    }
}
