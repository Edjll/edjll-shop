package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Refund;
import ru.edjll.shop.domain.StatusReviewAndRefund;
import ru.edjll.shop.service.RefundService;

@Controller
public class RefundController {

    @Autowired
    private RefundService refundService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/admin/refund/update/{id}")
    public String getRefundUpdate(
            @PathVariable Long id,
            Model model
    ) {

        Refund refund = refundService.getRefundById(id);

        model.addAttribute("refund", refund);
        model.addAttribute("statuses", StatusReviewAndRefund.values());

        return "admin/refund/update";
    }
}
