package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.model.Review;
import ru.edjll.shop.service.ReviewService;

import java.util.Collections;
import java.util.Map;

@RestController
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "review/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createReview(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "review", required = false) Review review,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        reviewService.saveReview(user, review);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/review/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateReview(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "review", required = false) Review review,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        reviewService.updateReview(user, review);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @PostMapping(value = "/admin/review/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> adminUpdateReview(
            @RequestPart(name = "review", required = false) Review review,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        reviewService.updateReviewStatus(review);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/review/delete")
    public ResponseEntity<Map<String, String>> deletePromotion(@RequestPart(name = "review") IdWrapper idWrapper) {
        try {
            reviewService.deleteReview(idWrapper.getId());
            return ResponseEntity.ok().body(null);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Удаление невозможно, есть связанные сущности"));
        }
    }
}
