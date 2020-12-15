package ru.edjll.shop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attribute_value")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "value_id", nullable = false)
    private Value value;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "product_attribute",
            joinColumns = @JoinColumn(name = "attribute_value_id"),
            inverseJoinColumns = @JoinColumn(name = "product_data_id")
    )
    private List<ProductData> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }
}
