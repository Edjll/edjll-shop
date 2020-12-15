package ru.edjll.shop.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.edjll.shop.validation.user.email.UniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@UniqueEmail
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.user.firstName.empty")
    @Column(
            name = "first_name",
            length = 40,
            nullable = false
    )
    private String firstName;

    @NotEmpty(message = "error.validation.user.lastName.empty")
    @Column(
            name = "last_name",
            length = 40,
            nullable = false
    )
    private String lastName;

    @Column(
            name = "patronymic",
            length = 40
    )
    private String patronymic;

    @Email
    @NotEmpty(message = "error.validation.user.email.empty")
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @NotEmpty(message = "error.validation.user.password.empty")
    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @NotEmpty(message = "error.validation.user.phone.empty")
    @Column(
            name = "phone",
            length = 15,
            nullable = false
    )
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "registration_date",
            length = 10,
            nullable = false,
            updatable = false
    )
    @CreationTimestamp
    private Date registrationDate;


    @OneToOne(mappedBy = "user")
    private Employee employee;

    @Column(
            name = "role",
            length = 10,
            nullable = false
    )
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    private Set<Role> roles;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Basket> baskets;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Sale> sales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(Set<Basket> baskets) {
        this.baskets = baskets;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
