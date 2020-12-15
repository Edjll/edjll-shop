package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "promotion_product")
public class PromotionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.promotionProduct.discount.empty")
    @Column(
            name = "discount",
            length = 3,
            nullable = false
    )
    private Integer discount;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_data_id", nullable = false)
    private ProductData productData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }
}
