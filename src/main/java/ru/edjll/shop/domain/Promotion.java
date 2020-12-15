package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.promotion.name.empty")
    @Column(
            name = "name",
            length = 50,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "error.validation.promotion.description.empty")
    @Column(
            name = "description",
            length = 150,
            nullable = false
    )
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "error.validation.promotion.dateStart.empty")
    @Column(
            name = "date_start",
            length = 150,
            nullable = false
    )
    private Date dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "error.validation.promotion.dateEnd.empty")
    @Column(
            name = "date_end",
            length = 150,
            nullable = false
    )
    private Date dateEnd;

    @OneToOne(mappedBy = "promotion")
    private Image banner;

    @OneToMany(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "promotion_id")
    private List<PromotionProduct> promotionProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }

    public void setPromotionProducts(List<PromotionProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

    public Image getBanner() {
        return banner;
    }

    public void setBanner(Image banner) {
        this.banner = banner;
    }
}
