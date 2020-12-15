package ru.edjll.shop.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.edjll.shop.validation.promotion.name.UniqueName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@UniqueName
public class Promotion {

    private Long id;

    @NotEmpty(message = "error.validation.promotion.name.empty")
    private String name;

    @NotEmpty(message = "error.validation.promotion.description.empty")
    private String description;

    @NotNull(message = "error.validation.promotion.dateStart.empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;

    @NotNull(message = "error.validation.promotion.dateEnd.empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;

    @NotEmpty(message = "error.validation.promotion.products.empty")
    private List<DiscountProduct> products;

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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<DiscountProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DiscountProduct> products) {
        this.products = products;
    }
}
