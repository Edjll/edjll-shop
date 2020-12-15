package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.model.Refund;
import ru.edjll.shop.service.RefundService;

@RestController
public class RefundRestController {

    @Autowired
    private RefundService refundService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/refund/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addRefund(
            @RequestPart(name = "refund") Refund refund
    ) {
        refundService.addRefund(refund);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/refund/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUserRefund(
            @RequestPart(name = "refund") Refund refund
    ) {
        refundService.updateUserRefund(refund);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @PostMapping(value = "/admin/refund/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRefund(
            @RequestPart(name = "refund") Refund refund
    ) {
        refundService.updateRefund(refund);
        return ResponseEntity.ok().body(null);
    }
}
