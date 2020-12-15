package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Review;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.repository.ReviewRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private ImageService imageService;

    public Review getReviewById(Long id) {
        return reviewRepository.getOne(id);
    }

    public List<Object> getProductsRating() {
        return reviewRepository.getProductsRating();
    }

    public Page<Review> getPageReview(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review findById(Long id) {
        return reviewRepository.getOne(id);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public Review getReviewByUserAndProductData(Long user, Long productData) {
        return reviewRepository.getByUserIdAndProductDataId(user, productData);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.getOne(id);

        reviewRepository.deleteById(id);
    }

    public void saveReview(User user, ru.edjll.shop.model.Review review) {
        Review reviewDomain = new Review();

        reviewDomain.setAdvantages(review.getAdvantages());
        reviewDomain.setDisadvantages(review.getDisadvantages());
        reviewDomain.setComment(review.getComment());
        reviewDomain.setDate(new Date());
        reviewDomain.setProductData(productDataService.getProductDataById(review.getProductData()));
        reviewDomain.setUser(user);
        reviewDomain.setRating(review.getRating());
        reviewDomain.setStatusReview(StatusReviewAndRefund.PROCESSING);

        reviewRepository.save(reviewDomain);
    }

    public void updateReviewStatus(ru.edjll.shop.model.Review review) {
        Review reviewDomain = reviewRepository.getOne(review.getId());
        reviewDomain.setStatusReview(
                Arrays.stream(StatusReviewAndRefund.values())
                        .filter(statusReview -> statusReview.ordinal() == review.getStatusReview())
                        .findFirst()
                        .orElse(null)
        );
        reviewRepository.save(reviewDomain);
    }

    public void updateReview(User user, ru.edjll.shop.model.Review review) {
        Review reviewDomain = reviewRepository.getOne(review.getId());

        reviewDomain.setAdvantages(review.getAdvantages());
        reviewDomain.setDisadvantages(review.getDisadvantages());
        reviewDomain.setComment(review.getComment());
        reviewDomain.setRating(review.getRating());
        reviewDomain.setStatusReview(StatusReviewAndRefund.PROCESSING);

        reviewRepository.save(reviewDomain);
    }

    public void confirmationReview(Long id) {
        Review review = reviewRepository.getOne(id);
        review.setStatusReview(StatusReviewAndRefund.CONFIRMED);
        reviewRepository.save(review);
    }

    public void unconfirmedReview(Long id) {
        Review review = reviewRepository.getOne(id);
        review.setStatusReview(StatusReviewAndRefund.UNCONFIRMED);
        reviewRepository.save(review);
    }

    public Page<Review> getReviewsByUser(User user, Pageable pageable) {
        return reviewRepository.getAllByUserId(user.getId(), pageable);
    }
}
