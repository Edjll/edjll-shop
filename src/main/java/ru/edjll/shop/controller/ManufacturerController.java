package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.domain.Manufacturer;
import ru.edjll.shop.service.CountryService;
import ru.edjll.shop.service.ImageService;
import ru.edjll.shop.service.ManufacturerService;

import java.util.List;
import java.util.Map;

@Controller
public class ManufacturerController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/manufacturer/create")
    public String getManufacturersCreate(Map<String, Object> model) {
        List<Country> countries = countryService.getAllCountries();
        model.put("countries", countries);
        return "admin/manufacturer/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/manufacturer/update/{id}")
    public String getManufacturersUpdate(
            @PathVariable Long id,
            Model model
    ) {

        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        String logo = imageService.getImageByManufacturerId(id);
        List<Country> countries = countryService.getAllCountries();

        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("logo", logo);
        model.addAttribute("countries", countries);

        return "admin/manufacturer/update";
    }
}
