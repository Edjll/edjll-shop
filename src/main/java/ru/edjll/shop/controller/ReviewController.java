package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Review;
import ru.edjll.shop.domain.StatusReviewAndRefund;
import ru.edjll.shop.service.ReviewService;

import java.util.Map;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/admin/review/update/{id}")
    public String confirmationReview(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Review review = reviewService.getReviewById(id);

        model.put("statuses", StatusReviewAndRefund.values());
        model.put("review", review);

        return "admin/support/review/update";
    }
}
