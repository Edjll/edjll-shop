package ru.edjll.shop.model;

import java.util.List;

public class AttributeSearch {
    private Long id;

    private List<Value> values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
