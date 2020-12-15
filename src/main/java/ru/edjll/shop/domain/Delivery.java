package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.delivery.address.empty")
    @Column(
            name = "address",
            length = 50,
            nullable = false
    )
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private StatusDelivery statusDelivery;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "sale_product_id", nullable = false)
    private SaleProduct saleProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StatusDelivery getStatusDelivery() {
        return statusDelivery;
    }

    public void setStatusDelivery(StatusDelivery statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public SaleProduct getSaleProduct() {
        return saleProduct;
    }

    public void setSaleProduct(SaleProduct saleProduct) {
        this.saleProduct = saleProduct;
    }
}
