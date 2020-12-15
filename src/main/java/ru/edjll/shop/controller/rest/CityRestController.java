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
import ru.edjll.shop.model.City;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.service.CityService;

import java.util.Collections;
import java.util.Map;

@RestController
public class CityRestController {
    @Autowired
    private CityService cityService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/city/create")
    public ResponseEntity<Map<String, String>> createCity(
            @RequestPart(name = "city", required = false) @Validated City city,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        cityService.save(city);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/city/update")
    public ResponseEntity<Map<String, String>> updateCity(
            @RequestPart(name = "city", required = false) @Validated City city,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        cityService.update(city);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/city/delete")
    public ResponseEntity<Map<String, String>> deleteCity(@RequestPart(name = "city") IdWrapper idWrapper) {
        try {
            cityService.deleteCity(idWrapper.getId());
            return ResponseEntity.ok().body(null);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Удаление невозможно, есть связанные сущности"));
        }
    }
}
