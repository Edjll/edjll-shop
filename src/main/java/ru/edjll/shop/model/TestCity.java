package ru.edjll.shop.model;

import ru.edjll.shop.domain.Country;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class TestCity {
    private Long id;

    @NotEmpty(message = "error.validation.value.value.empty")
    private String name;
    @Valid
    private Country country;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
