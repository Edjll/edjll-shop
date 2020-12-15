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
import ru.edjll.shop.domain.Attribute;
import ru.edjll.shop.service.AttributeService;

import java.util.List;
import java.util.Set;

@RestController
public class AttributeRestController {

    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/attribute/get/name")
    public ResponseEntity<Page<ru.edjll.shop.model.product.category.Attribute>> getAllAttributeName(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "excluded") List<Long> attributes,
            @RequestParam(required = false, name = "searchParam", defaultValue = "") String searchParam
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ru.edjll.shop.model.product.category.Attribute> attributePage = attributeService.getAllAttributeNamePage(pageable, attributes, searchParam);
        return ResponseEntity.ok().body(attributePage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/attribute/get/all")
    public ResponseEntity<Page<ru.edjll.shop.model.product.category.Attribute>> getAllAttribute(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "excluded") List<String> attributes,
            @RequestParam(required = false, name = "searchParam", defaultValue = "") String searchParam,
            @RequestParam(required = false, name = "tracking", defaultValue = "") String tracking
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ru.edjll.shop.model.product.category.Attribute> attributePage = attributeService.getAllAttributePage(pageable, attributes, searchParam, tracking);
        return ResponseEntity.ok().body(attributePage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/attribute/get/info")
    public ResponseEntity<ru.edjll.shop.model.product.Attribute> getInfoForAttribute(
            @RequestParam(required = false, name = "name", defaultValue = "") String name
    ) {
        Attribute attribute = attributeService.getAttributeByName(name);
        if (attribute != null) {
            return ResponseEntity.ok().body(new ru.edjll.shop.model.product.Attribute(attribute.getCategory().getName(), attribute.getDescription()));
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/attribute/get/productCategory")
    public ResponseEntity<Set<ru.edjll.shop.model.Attribute>> getAttributesByProductCategory(
            @RequestParam("category") Long id
    ) {
        Set<ru.edjll.shop.model.Attribute> attributes = attributeService.getAttributesByProductCategory(id);
        return ResponseEntity.ok().body(attributes);
    }
}
