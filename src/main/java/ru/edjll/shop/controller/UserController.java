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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.service.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleProductService saleProductService;

    @Autowired
    private RefundService refundService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile")
    public String getUserProfilePage(Map<String, Object> model) {
        List<City> cities = cityService.getAllCities();
        model.put("cities", cities);
        return "main/user/profile";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/password")
    public String getUserProfilePasswordPage() {
        return "main/user/changePassword";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/support")
    public String getQuestions(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Question> questions = questionService.getQuestionsByUser(pageable, user);
        model.put("questionsPage", questions);
        return "main/user/support";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/support/question/{id}")
    public String getQuestion(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Question question = questionService.getQuestionById(id);
        model.put("question", question);
        return "main/user/question";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/reviews")
    public String getReviews(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Review> reviews = reviewService.getReviewsByUser(user, pageable);
        model.put("reviewsPage", reviews);
        return "main/user/review";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/reviews/{id}")
    public String getReview(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Review review = reviewService.findById(id);
        model.put("review", review);
        return "main/user/review";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/sales")
    public String getSales(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Sale> sales = saleService.getSalesPageByUser(user.getId(), pageable);
        model.put("salesPage", sales);
        return "main/user/sale";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/refunds")
    public String getRefunds(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Refund> refunds = refundService.getRefundsPageByUser(user, pageable);
        model.put("refundsPage", refunds);
        return "main/user/refunds";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/sales/{id}")
    public String getSale(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @PathVariable Long id
    ) {
        List<SaleProduct> sales = saleProductService.getSaleProductsBySale(id);
        model.put("sales", sales);
        return "main/sale/index";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/refunds/{id}")
    public String getRefund(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @PathVariable Long id
    ) {
        Refund refund = refundService.getRefundById(id);
        model.put("refund", refund);
        model.put("statuses", TypeRefund.values());
        return "main/user/refund/index";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/sales/refund/{id}")
    public String getSaleRefund(
            @AuthenticationPrincipal User user,
            Map<String, Object> model,
            @PathVariable Long id
    ) {
        SaleProduct saleProduct = saleProductService.getSaleProductById(id);
        model.put("saleProduct", saleProduct);
        model.put("statuses", TypeRefund.values());
        return "main/user/refund/create";
    }
}
