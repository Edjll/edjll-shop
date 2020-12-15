package ru.edjll.shop.domain;

import ru.edjll.shop.validation.city.name.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@UniqueName
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.city.name.empty")
    @Column(
            name = "name",
            length = 30,
            nullable = false
    )
    private String name;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "country_id", nullable = false)
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
