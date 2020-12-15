package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.cart.CartProduct;
import ru.edjll.shop.model.User;
import ru.edjll.shop.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class RegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping(value = "/user/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView saveUser(
            @RequestPart(name = "user") User user,
            @RequestPart(name = "cart") List<CartProduct> cart,
            HttpServletRequest httpServletRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return new ModelAndView("redirect:/user/registration", errors);
        }
        ru.edjll.shop.domain.User userDomain = registrationService.save(user);
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        httpServletRequest.setAttribute("user", userDomain);
        return new ModelAndView("redirect:/login");
    }
}
