package ru.edjll.shop.controller.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.cart.CartProduct;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.service.BasketService;
import ru.edjll.shop.service.SecurityService;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@RestController
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value = "/basket/add/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "product") CartProduct product
    ) {
        return ResponseEntity.ok().body(basketService.save(user, product));
    }

    @GetMapping(value = "/basket/get/product/count")
    public Integer getCountProduct(
            @AuthenticationPrincipal User user
    ) {
        return basketService.getCountProduct(user);
    }

    @GetMapping(value = "/basket/get/product")
    public ResponseEntity<List<ProductDataCart>> getBasket(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "products", required = false) String cart
    ) {
        if (user != null) {
            return ResponseEntity.ok().body(basketService.getAllProductsInBasket(user));
        }
        if (cart != null) {
            try {
                Gson gson = new Gson();
                Reader reader = new StringReader(cart);
                List<CartProduct> products = gson.fromJson(reader, new TypeToken<List<CartProduct>>() {}.getType());
                return ResponseEntity.ok().body(basketService.getProductsInBasket(products));
            } catch (JsonParseException exception) {
                exception.printStackTrace();
            }
        }
        return ResponseEntity.ok().body(null);
    }


    @PostMapping(value = "/basket/delete/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> deleteProduct(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "product", required = false) CartProduct product
    ) {
        return ResponseEntity.ok().body(basketService.delete(user, product));
    }
}
