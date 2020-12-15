package ru.edjll.shop.model;

import ru.edjll.shop.validation.manufacturer.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@UniqueName
public class Manufacturer {
    private Long id;

    @NotEmpty(message = "error.validation.manufacturer.name.empty")
    private String name;

    @NotEmpty(message = "error.validation.manufacturer.description.empty")
    private String description;

    @NotNull(message = "error.validation.manufacturer.country.null")
    private Long country;

    public Manufacturer(Long id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
