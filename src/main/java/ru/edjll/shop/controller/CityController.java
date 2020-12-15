package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.City;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.service.CityService;
import ru.edjll.shop.service.CountryService;

import java.util.List;
import java.util.Map;

@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/city/create")
    public String getCreateCityPage(Map<String, Object> model) {
        List<Country> countries = countryService.getAllCountries();
        model.put("countries", countries);
        return "admin/city/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/city/update/{id}")
    public String getUpdateCityPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        City city = cityService.getCity(id);
        List<Country> countries = countryService.getAllCountries();

        model.put("city", city);
        model.put("countries", countries);

        return "admin/city/update";
    }
}
