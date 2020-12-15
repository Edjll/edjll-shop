package ru.edjll.shop.model;

import java.util.List;

public class Search {
    private Long category;

    private Integer min;

    private Integer max;

    private Integer sort;

    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    private List<AttributeSearch> attributes;

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<AttributeSearch> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeSearch> attributes) {
        this.attributes = attributes;
    }
}
