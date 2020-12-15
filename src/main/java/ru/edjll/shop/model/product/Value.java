package ru.edjll.shop.model.product;

public class Value {

    private Long id;
    private String value;
    private String unit;

    public Value() { }

    public Value(ru.edjll.shop.domain.Value value) {
        this.value = value.getValue();
        this.unit = value.getUnit();
        this.id = value.getId();
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
