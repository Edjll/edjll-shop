package ru.edjll.shop.model.shop;

import ru.edjll.shop.domain.AttributeValue;

public class Attribute {

    private Long attributeId;
    private String name;
    private Long valueId;
    private String value;
    private String unit;

    public Attribute() {
    }

    public Attribute(AttributeValue attributeValue) {
        this.attributeId = attributeValue.getAttribute().getId();
        this.name = attributeValue.getAttribute().getName();
        this.valueId = attributeValue.getValue().getId();
        this.value = attributeValue.getValue().getValue();
        this.unit = attributeValue.getValue().getUnit();
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
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
