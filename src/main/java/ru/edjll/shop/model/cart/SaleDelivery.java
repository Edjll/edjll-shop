package ru.edjll.shop.model.cart;

import java.util.List;

public class SaleDelivery {
    private Long id;

    private List<Delivery> deliveries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
