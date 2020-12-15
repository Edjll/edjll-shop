package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.model.product.Value;
import ru.edjll.shop.service.ValueService;

@RestController
public class ValueRestController {

    @Autowired
    private ValueService valueService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/value/get/all")
    public ResponseEntity getAllValue(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "searchParam", defaultValue = "") String searchParam
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Value> valuePage = valueService.getAllValuePage(pageable, searchParam);
        return ResponseEntity.ok().body(valuePage);
    }
}
