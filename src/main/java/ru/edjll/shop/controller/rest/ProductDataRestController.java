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
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.model.rest.ProductData;
import ru.edjll.shop.service.ProductDataService;
import ru.edjll.shop.validation.productData.ProductDataValidator;

import java.util.List;
import java.util.Map;

@RestController
public class ProductDataRestController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private ProductDataValidator productDataValidator;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createProduct(
            @RequestPart(name = "product", required = false) @Validated ru.edjll.shop.model.ProductData productData,
            @RequestPart(name = "productImage", required = false) List<MultipartFile> image,
            BindingResult bindingResult
    ) {
        Map<String, String> productDataValidatorErrors = productDataValidator.validate(productData);
        if (bindingResult.hasErrors() || productDataValidatorErrors.size() > 0) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            errors.putAll(productDataValidatorErrors);
            return ResponseEntity.badRequest().body(errors);
        }
        productDataService.save(productData, image);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/product/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateProduct(
            @RequestPart(name = "product", required = false) @Validated ru.edjll.shop.model.ProductData productData,
            @RequestPart(name = "productImage", required = false) List<MultipartFile> image,
            BindingResult bindingResult
    ) {
        Map<String, String> productDataValidatorErrors = productDataValidator.validate(productData);
        if (bindingResult.hasErrors() || productDataValidatorErrors.size() > 0) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            errors.putAll(productDataValidatorErrors);
            return ResponseEntity.badRequest().body(errors);
        }
        productDataService.update(productData, image);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/product/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> deleteProduct(
            @RequestPart(name = "product", required = false) IdWrapper idWrapper
    ) {
        Map<String, String> errors = productDataService.delete(idWrapper.getId());
        if (errors.isEmpty()) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/product/get", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Page<ProductData>> getProducts(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) String searchParam,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "excluded") List<Long> products
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductData> productPage = productDataService.getPageProductsSearch(pageable, searchParam, products);
        return ResponseEntity.ok().body(productPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/product/get/without/promotion", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Page<ProductData>> getProductsWithoutPromotions(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "") String searchParam,
            @RequestParam(required = false, defaultValue = "2") Integer pageSize,
            @RequestParam(required = false, name = "excluded") List<Long> products
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductData> productPage = productDataService.getPageProductsSearchWithoutPromotions(pageable, searchParam, products);
        return ResponseEntity.ok().body(productPage);
    }
}
