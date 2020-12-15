package ru.edjll.shop.model;

import ru.edjll.shop.validation.city.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@UniqueName
public class City {
    private Long id;

    @NotEmpty(message = "error.validation.city.name.empty")
    private String name;

    @NotNull(message = "error.validation.city.country.null")
    private Long country;

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

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
