package ru.edjll.shop.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sale_product")
public class SaleProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "cost",
            length = 10,
            nullable = false
    )
    private Double cost;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(mappedBy = "saleProduct")
    private Refund refund;

    @OneToOne(mappedBy = "saleProduct", fetch = FetchType.EAGER)
    private Delivery delivery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
