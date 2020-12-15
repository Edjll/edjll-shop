package ru.edjll.shop.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_data")
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "name",
            length = 100
    )
    private String name;

    @Column(
            name = "description",
            length = 500
    )
    private String description;

    @Column(
            name = "cost"
    )
    private Double cost;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_category_id")
    private ProductCategory category;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "product_attribute",
            joinColumns = @JoinColumn(name = "product_data_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributes;

    @OneToMany(mappedBy = "productData")
    private List<Product> products;

    @OneToMany(mappedBy = "productData")
    private List<Image> images;

    @OneToMany(mappedBy = "productData")
    private List<PromotionProduct> promotionProducts;

    @OneToMany(mappedBy = "productData")
    private List<Review> reviews;

    @OneToMany(mappedBy = "productData")
    private List<Basket> baskets;

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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Set<AttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeValue> attributes) {
        this.attributes = attributes;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }

    public void setPromotionProducts(List<PromotionProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
}
