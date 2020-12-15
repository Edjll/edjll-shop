package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.domain.Sale;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.Payment;
import ru.edjll.shop.model.cart.SaleDelivery;
import ru.edjll.shop.service.SaleService;

@RestController
public class SaleRestController {

    @Autowired
    private SaleService saleService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/shop/cart/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String sale(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "payment") Payment payment
    ) {
        Sale sale = saleService.sale(user, payment);
        return "/user/profile/sales/" + sale.getId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/sale/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateSale(
            @RequestPart(name = "sale") SaleDelivery saleDelivery
    ) {
        saleService.saleUpdate(saleDelivery);
        return ResponseEntity.ok().body(null);
    }
}
