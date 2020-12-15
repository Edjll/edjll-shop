package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "value")
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.value.value.empty")
    @Column(
            name = "value",
            nullable = false
    )
    private String value;

    @NotEmpty(message = "error.validation.value.unit.empty")
    @Column(
            name = "unit",
            nullable = false
    )
    private String unit;

    @OneToMany(mappedBy = "value")
    private List<AttributeValue> attributeValues;

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
