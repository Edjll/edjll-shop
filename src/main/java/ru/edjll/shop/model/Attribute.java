package ru.edjll.shop.model;

import ru.edjll.shop.model.product.Value;
import ru.edjll.shop.validation.attribute.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@UniqueName
public class Attribute {

    private Long id;

    @NotEmpty(message = "error.validation.attribute.name.empty")
    private String name;

    @NotEmpty(message = "error.validation.attribute.description.empty")
    private String description;

    @NotEmpty(message = "error.validation.attribute.value.empty")
    private String value;

    @NotEmpty(message = "error.validation.attribute.unit.empty")
    private String unit;

    private String category;

    private Set<Value> values;

    public Attribute() {
    }

    public Attribute(ru.edjll.shop.domain.Attribute attribute) {
        this.id = attribute.getId();
        this.name = attribute.getName();
        this.description = attribute.getDescription();
        this.category = attribute.getCategory() != null ? attribute.getCategory().getName() : null;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }
}
