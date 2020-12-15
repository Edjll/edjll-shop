package ru.edjll.shop.model.product.category;

public class Attribute {

    private Long id;
    private String name;

    public Attribute() { }

    public Attribute(ru.edjll.shop.domain.Attribute attribute) {
        this.id = attribute.getId();
        this.name = attribute.getName();
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
