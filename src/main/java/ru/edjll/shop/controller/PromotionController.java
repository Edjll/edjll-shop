package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.shop.domain.Basket;
import ru.edjll.shop.domain.Promotion;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.service.BasketService;
import ru.edjll.shop.service.ImageService;
import ru.edjll.shop.service.ProductDataService;
import ru.edjll.shop.service.PromotionService;

import java.util.List;
import java.util.Map;

@Controller
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private BasketService basketService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/promotion/create")
    public String getPromotionPage() {
        return "admin/promotion/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/promotion/update/{id}")
    public String getPromotionUpdatePage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Promotion promotion = promotionService.findById(id);
        String banner = imageService.getImageByPromotionId(id);

        model.put("promotion", promotion);
        model.put("banner", banner);
        return "admin/promotion/update";
    }

    @GetMapping("/promotions/{id}/products")
    public String getPromotionProducts(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Promotion promotion = promotionService.getPromotionById(id);
        Page<ProductDataCart> productDataCarts = productDataService.getProductDataCartsByPromotion(pageable, promotion.getId());
        List<Basket> baskets = null;

        if (user != null) {
            baskets = basketService.getBasketsByUser(user.getId());
        }

        model.put("baskets", baskets);

        model.put("promotion", promotion);
        model.put("productsPage", productDataCarts);

        return "main/shop/promotion/products";
    }
}
