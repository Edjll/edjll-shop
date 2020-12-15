package ru.edjll.shop.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import ru.edjll.shop.domain.Role;
import ru.edjll.shop.validation.user.email.UniqueEmail;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@UniqueEmail
public class User {

    private Long id;

    @NotEmpty(message = "error.validation.user.firstName.empty")
    private String firstName;

    @NotEmpty(message = "error.validation.user.lastName.empty")
    private String lastName;

    private String patronymic;

    @Email
    @NotEmpty(message = "error.validation.user.email.empty")
    private String email;

    @NotEmpty(message = "error.validation.user.password.empty")
    private String password;

    @NotEmpty(message = "error.validation.user.phone.empty")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date registrationDate;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    private Set<Role> roles;

    private Long city;

    public User() { }

    public User(ru.edjll.shop.domain.User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.registrationDate = user.getRegistrationDate();
        this.city = user.getCity().getId();
    }

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

    public String getPassword() {
        return password;
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

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
