package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.model.Category;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.service.ProductCategoryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProductCategoryRestController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/{id}/children")
    public List<Object> getChildCategories(@PathVariable Long id) {
        List<ProductCategory> categories = productCategoryService.getChildCategories(id);
        return categories.stream()
                .map(category -> new Object() {
                        public Long id = category.getId();
                        public String name = category.getName();
                    }
                ).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/children/parent/null")
    public List<Object> getCategoriesWithoutParent() {
        List<ProductCategory> categories = productCategoryService.getCategoriesWithoutParent();
        return categories.stream()
                .map(category -> new Object() {
                            public Long id = category.getId();
                            public String name = category.getName();
                        }
                ).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/children/null")
    public ResponseEntity<Object> getCategoriesWithoutChildren(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchParam
    ) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Category> categories = productCategoryService.getCategoriesWithoutChildren(pageable, searchParam);
            return ResponseEntity.ok().body(categories);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/category/{id}/children/all")
    public ResponseEntity<Object> getCategoriesChildrenAll(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchParam
    ) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<ru.edjll.shop.model.shop.ProductCategory> categoryPage = productCategoryService.getProductCategoryShopModel(id, searchParam, pageable);
            return ResponseEntity.ok().body(categoryPage);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/category/children/all")
    public ResponseEntity<Object> getCategoriesAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchParam
    ) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<ru.edjll.shop.model.shop.ProductCategory> categoryPage = productCategoryService.getAllProductCategoriesModelShop(searchParam, pageable);
            return ResponseEntity.ok().body(categoryPage);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/children")
    public List<Category> getAllCategoryModel() {
        return productCategoryService.getAllCategoriesModel();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/category/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createCategory(@RequestPart(name = "productCategory") @Validated ru.edjll.shop.model.ProductCategory productCategory,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> nonValid = productCategoryService.saveProductCategory(productCategory);
        if (nonValid.isEmpty()) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.badRequest().body(nonValid);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/category/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateCategory(@RequestPart(name = "productCategory") @Validated ru.edjll.shop.model.ProductCategory productCategory,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> nonValid = productCategoryService.updateCategory(productCategory);
        if (nonValid.isEmpty()) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.badRequest().body(nonValid);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/category/delete")
    public ResponseEntity<Map<String, String>> deleteCategory(@RequestPart(name = "productCategory") IdWrapper idWrapper) {
        if (productCategoryService.deleteCategoryById(idWrapper.getId())) {
            return ResponseEntity.ok().body(Collections.singletonMap("errors", "Удаление успешно"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("errors", "Не удалось удалить категорию #" + idWrapper.getId() + ", т.к. у неё есть продукты"));
    }
}
