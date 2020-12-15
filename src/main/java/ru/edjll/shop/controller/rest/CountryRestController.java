package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.service.CountryService;

import java.util.Collections;
import java.util.Map;

@RestController
public class CountryRestController {
    @Autowired
    private CountryService countryService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/country/create")
    public ResponseEntity<Map<String, String>> createCountry(
            @RequestPart(name = "country", required = false) @Validated Country country,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        countryService.saveCountry(country);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/country/update")
    public ResponseEntity<Map<String, String>> updateCountry(
            @RequestPart(name = "country", required = false) @Validated Country country,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        countryService.saveCountry(country);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/country/delete")
    public ResponseEntity<Map<String, String>> deleteCountry(@RequestPart(name = "country") IdWrapper idWrapper) {
        try {
            countryService.deleteCountryById(idWrapper.getId());
            return ResponseEntity.ok().body(null);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Удаление невозможно, есть связанные сущности"));
        }
    }
}
