package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.edjll.shop.domain.City;
import ru.edjll.shop.service.CityService;
import ru.edjll.shop.service.RegistrationService;

import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CityService cityService;

    @GetMapping("/user/registration")
    public String getRegistrationPage(Map<String, Object> model){
        List<City> cities = cityService.getAllCities();
        model.put("cities", cities);
        return "main/user/registration";
    }
}
