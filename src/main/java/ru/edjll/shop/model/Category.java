package ru.edjll.shop.model;

import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.validation.productCategory.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@UniqueName
public class Category {
    private Long id;

    @NotEmpty(message = "error.validation.productCategory.name.empty")
    private String name;

    private Long parent;

    private Boolean editable;

    private List<Category> children;

    public Category() {
    }

    public Category(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
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

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public void setConvertedChildren(List<ProductCategory> children) {
        this.children = children.stream().map(child -> {
            Category category = new Category();
            category.setId(child.getId());
            category.setName(child.getName());
            category.setConvertedChildren(child.getChildren());
            category.setEditable(child.getProducts().isEmpty());
            return category;
        }).collect(Collectors.toList());
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
}
