package ru.edjll.shop.model;

public class SupplyProductData {
    private Integer count;

    private Long product;

    private String productName;

    public SupplyProductData() { }

    public SupplyProductData(Integer count, String productName) {
        this.count = count;
        this.productName = productName;
    }

    public SupplyProductData(Long count, String productName) {
        this.count = count.intValue();
        this.productName = productName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
