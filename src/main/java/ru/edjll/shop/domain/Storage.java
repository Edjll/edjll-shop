package ru.edjll.shop.domain;

import ru.edjll.shop.validation.storage.name.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@UniqueName
@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.storage.name.empty")
    @Column(
            name = "name",
            length = 40,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "error.validation.storage.description.empty")
    @Column(
            name = "description",
            length = 150,
            nullable = false
    )
    private String description;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
