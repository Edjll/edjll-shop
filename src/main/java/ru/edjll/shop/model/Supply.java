package ru.edjll.shop.model;

import java.util.Date;
import java.util.List;

public class Supply {
    private Long id;

    private Date date;

    private Long storage;

    private String storageName;

    private List<SupplyProductData> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getStorage() {
        return storage;
    }

    public void setStorage(Long storage) {
        this.storage = storage;
    }

    public List<SupplyProductData> getProducts() {
        return products;
    }

    public void setProducts(List<SupplyProductData> products) {
        this.products = products;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}
