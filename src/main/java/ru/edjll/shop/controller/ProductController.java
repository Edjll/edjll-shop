package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.service.*;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private AttributeCategoryService attributeCategoryService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/shop/product/{id}")
    public String getProductPage(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        ProductData productData = productDataService.getProductDataById(id);
        List<AttributeCategory> attributeCategories = attributeCategoryService.getAttributeCategoriesByProductDataId(productData.getId());
        Long maxCount = productService.getProductMaxCount(productData.getId());
        Review review = null;
        Basket basket = null;
        if (user != null) {
            review = reviewService.getReviewByUserAndProductData(user.getId(), productData.getId());
            basket = basketService.getBasketByProductData(productData.getId());
        }
        Integer discount = productDataService.getDiscount(productData);

        model.put("discount", discount);
        model.put("productData", productData);
        model.put("attributeCategories", attributeCategories);
        model.put("maxCount", maxCount);
        model.put("review", review);
        model.put("basket", basket);

        return "main/shop/product";
    }
}
