package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Sale;
import ru.edjll.shop.domain.StatusDelivery;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.service.BasketService;
import ru.edjll.shop.service.SaleService;

import java.util.List;
import java.util.Map;

@Controller
public class SaleController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private BasketService basketService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/shop/cart/payment")
    public String getSale(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        List<ProductDataCart> cartProducts = basketService.getProductsInBasket(user);
        model.put("cartProducts", cartProducts);
        return "main/shop/payment";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/sale/update/{id}")
    public String getUpdateSalePage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Sale sale = saleService.getSale(id);
        model.put("sale", sale);
        model.put("statuses", StatusDelivery.values());
        return "admin/sale/update";
    }
}
