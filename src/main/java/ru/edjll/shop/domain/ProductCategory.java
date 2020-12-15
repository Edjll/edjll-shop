package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.productCategory.name.empty")
    @Column(
            name = "name",
            length = 30,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "error.validation.productCategory.description.empty")
    @Column(
            name = "description",
            length = 300,
            nullable = false
    )
    private String description;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "parent_id")
    private List<ProductCategory> children;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "product_category_id")
    private Set<ProductData> products;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "attribute_product_category",
            joinColumns = @JoinColumn(name = "product_category_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private Set<Attribute> attributes;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

    public Set<ProductData> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductData> products) {
        this.products = products;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
