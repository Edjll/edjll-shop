package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_review")
    private StatusReviewAndRefund statusReview;

    @NotEmpty(message = "error.validation.review.advantages.empty")
    @Column(
            name = "advantages",
            nullable = false
    )
    private String advantages;

    @NotEmpty(message = "error.validation.review.disadvantages.empty")
    @Column(
            name = "disadvantages",
            nullable = false
    )
    private String disadvantages;

    @NotEmpty(message = "error.validation.review.comment.empty")
    @Column(
            name = "comment",
            nullable = false
    )
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "date",
            length = 10
    )
    private Date date;

    @NotEmpty(message = "error.validation.review.rating.empty")
    @Column(
            name = "rating",
            length = 1,
            nullable = false
    )
    private Integer rating;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_data_id", nullable = false)
    private ProductData productData;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusReviewAndRefund getStatusReview() {
        return statusReview;
    }

    public void setStatusReview(StatusReviewAndRefund statusReview) {
        this.statusReview = statusReview;
    }

    public String getAdvantages() {
        return advantages;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
