package ru.edjll.shop.model;

public class Review {
    private Long id;

    private Long productData;

    private String productDataName;

    private String advantages;

    private String disadvantages;

    private String comment;

    private Integer rating;

    private Integer statusReview;

    public String getProductDataName() {
        return productDataName;
    }

    public void setProductDataName(String productDataName) {
        this.productDataName = productDataName;
    }

    public Integer getStatusReview() {
        return statusReview;
    }

    public void setStatusReview(Integer statusReview) {
        this.statusReview = statusReview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductData() {
        return productData;
    }

    public void setProductData(Long productData) {
        this.productData = productData;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
