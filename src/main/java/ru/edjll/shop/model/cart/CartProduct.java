package ru.edjll.shop.model.cart;

public class CartProduct {

    private Long id;

    private Integer count;

    public CartProduct() { }

    public CartProduct(Long id, Integer count) {
        this.id = id;
        this.count = count;
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
}
