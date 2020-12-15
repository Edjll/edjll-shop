package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "attribute_category")
public class AttributeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "error.validation.attributeCategory.name.empty")
    @Column(
            name = "name",
            length = 40,
            nullable = false
    )
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Attribute> attributes;

    public AttributeCategory() { }

    public AttributeCategory(Long id, @NotEmpty(message = "error.validation.attributeCategory.name.empty") String name) {
        this.id = id;
        this.name = name;
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
