package ru.edjll.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.manufacturer.name.empty")
    @Column(
            name = "name",
            length = 40,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "error.validation.manufacturer.description.empty")
    @Column(
            name = "description",
            length = 300,
            nullable = false
    )
    private String description;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToOne(mappedBy = "manufacturer")
    private Image logo;

    @OneToMany(mappedBy = "manufacturer")
    private List<ProductData> productData;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public List<ProductData> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductData> productData) {
        this.productData = productData;
    }
}
