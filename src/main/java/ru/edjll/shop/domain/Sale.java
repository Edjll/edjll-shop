package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "date",
            length = 10,
            nullable = false,
            columnDefinition = "datetime"
    )
    private LocalDateTime date;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    private Set<SaleProduct> saleProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(Set<SaleProduct> saleProducts) {
        this.saleProducts = saleProducts;
    }
}
