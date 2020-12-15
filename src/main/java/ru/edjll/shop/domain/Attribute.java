package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * An entity that describes a product characteristic.
 *
 * @author edjll
 */

@Entity
@Table(name = "attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.attribute.name.empty")
    @Column(
            name = "name",
            length = 50,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "error.validation.attribute.description.empty")
    @Column(
            name = "description",
            length = 150
    )
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_category_id")
    private AttributeCategory category;

    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> attributeValues;

    @ManyToMany(mappedBy = "attributes")
    private List<ProductCategory> productCategories;

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

    public AttributeCategory getCategory() {
        return category;
    }

    public void setCategory(AttributeCategory category) {
        this.category = category;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
