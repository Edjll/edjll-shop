package ru.edjll.shop.domain;

import javax.persistence.*;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "count",
            length = 3,
            nullable = false
    )
    private Integer count;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_data_id", nullable = false)
    private ProductData productData;

    public Basket() {
    }

    public Basket(User user, ProductData productData) {
        this.user = user;
        this.productData = productData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }
}
