package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.Supply;
import ru.edjll.shop.service.SupplyService;

import java.util.Map;

@RestController
public class SupplyRestController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SupplyService supplyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/supply/create")
    public ResponseEntity createSupply(
            @RequestPart(name = "supply", required = false) Supply supply,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        supplyService.saveSupply(supply);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/supply/update")
    public ResponseEntity updateSupply(
            @RequestPart(name = "supply") Supply supply,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        supplyService.updateSupply(supply);
        return ResponseEntity.ok().body(null);
    }
}
