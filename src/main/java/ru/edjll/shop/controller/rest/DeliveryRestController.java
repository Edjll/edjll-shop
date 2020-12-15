package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.service.DeliveryService;

@RestController
public class DeliveryRestController {

    @Autowired
    private DeliveryService deliveryService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/delivery/user/addresses")
    public ResponseEntity<Page<String>> getDeliveryAddresses(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchParam
    ) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<String> addresses = deliveryService.getUserAddresses(pageable, searchParam, user);
            return ResponseEntity.ok().body(addresses);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
