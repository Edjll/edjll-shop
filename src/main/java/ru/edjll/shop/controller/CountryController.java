package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.service.CountryService;

@Controller
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/country/create")
    public String getCountriesCreate() {
        return "admin/country/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/country/update/{id}")
    public String getCountriesUpdate(
            @PathVariable Long id,
            Model model
    ) {
        Country country = countryService.getCountryById(id);
        model.addAttribute("country", country);
        return "admin/country/update";
    }
}
