package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.service.AttributeService;
import ru.edjll.shop.service.ProductCategoryService;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/category/create")
    public String getCategoryCreate() {
        return "admin/category/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/category/update/{id}")
    public String getCategoryUpdate(
            @PathVariable Long id,
            Model model
    ) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);
        Set<Attribute> attributes = attributeService.getAttributesByProductCategoryWithoutYourself(productCategory.getId());
        productCategory.getProducts().forEach(productData -> {
            attributes.addAll(productData.getAttributes()
                    .stream()
                    .map(attributeValue -> new Attribute(attributeValue.getAttribute()))
                    .collect(Collectors.toSet()));
        });

        model.addAttribute("attributes", attributes);
        model.addAttribute("category", productCategory);
        return "admin/category/update";
    }
}
