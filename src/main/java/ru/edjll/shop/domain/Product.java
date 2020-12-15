package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "error.validation.product.productionDate.empty")
    @Column(
            name = "production_date",
            nullable = false
    )
    private Date productionDate;

    @NotEmpty(message = "error.validation.product.rejection.empty")
    @Column(
            name = "rejection",
            nullable = false
    )
    private Boolean rejection;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_data_id", nullable = false)
    private ProductData productData;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "supply_id", nullable = false)
    private Supply supply;

    @OneToMany(mappedBy = "product")
    private List<SaleProduct> saleProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Boolean getRejection() {
        return rejection;
    }

    public void setRejection(Boolean rejection) {
        this.rejection = rejection;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public List<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(List<SaleProduct> saleProduct) {
        this.saleProducts = saleProduct;
    }
}
