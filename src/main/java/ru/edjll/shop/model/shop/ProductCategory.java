package ru.edjll.shop.model.shop;

public class ProductCategory {

    private Long id;
    private String name;

    public ProductCategory() {
    }

    public ProductCategory(ru.edjll.shop.domain.ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
    }

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
}
