package ru.edjll.shop.model;

import ru.edjll.shop.validation.storage.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@UniqueName
public class Storage {
    private Long id;

    @NotEmpty(message = "error.validation.storage.name.empty")
    private String name;

    @NotEmpty(message = "error.validation.storage.description.empty")
    private String description;

    @NotNull(message = "error.validation.storage.city.null")
    private Long city;

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

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
