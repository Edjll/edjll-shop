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
import ru.edjll.shop.model.CategoryAttribute;
import ru.edjll.shop.service.AttributeCategoryService;
import ru.edjll.shop.service.AttributeService;

@RestController
public class AttributeCategoryRestController {

    @Autowired
    private AttributeCategoryService attributeCategoryService;

    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/attributeCategories/get")
    public ResponseEntity<Object> getAttributeCategories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchParam
    ) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<CategoryAttribute> attributeCategoryPage = attributeCategoryService.getAttributeCategoryPage(pageable, searchParam);
            return ResponseEntity.ok().body(attributeCategoryPage);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/attribute/get")
    public Object getAttributes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "attributeCategory") Long attributeCategoryId
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return attributeService.getAllAttributesByAttributeCategory(pageable, attributeCategoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/category/get/attribute")
    public Object getAttributesAndValues(
            @RequestParam(required = false, name = "category") Long id
    ) {
        return attributeCategoryService.getAttributesAndValues(id);
    }
}
