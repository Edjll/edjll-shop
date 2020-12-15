package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.shop.domain.Basket;
import ru.edjll.shop.domain.Manufacturer;
import ru.edjll.shop.domain.Promotion;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.service.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/")
    public String getMainPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        Promotion promotion = promotionService.findActivePromotion();
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        List<ProductDataCart> productDataCarts = productDataService.getPopularProducts();
        List<Basket> baskets = null;

        if (user != null) {
            baskets = basketService.getBasketsByUser(user.getId());
        }

        model.put("baskets", baskets);
        model.put("promotion", promotion);
        model.put("manufacturers", manufacturers);
        model.put("products", productDataCarts);

        return "main/index";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "main/about";
    }

    @GetMapping("/delivery")
    public String getDeliveryPage() {
        return "main/delivery";
    }

    @PostMapping("/add/email")
    public String addEmail(
            @RequestParam(name = "email") String email
    ) {
        emailService.saveEmail(email);
        return "redirect:/";
    }
}
