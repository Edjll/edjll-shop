package ru.edjll.shop.domain;

import ru.edjll.shop.validation.country.name.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@UniqueName
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.country.name.empty")
    @Column(
            name = "name",
            length = 30,
            nullable = false
    )
    private String name;

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
}
