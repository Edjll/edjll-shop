package ru.edjll.shop.domain;

import ru.edjll.shop.validation.employee.employmentContract.UniqueEmploymentContract;
import ru.edjll.shop.validation.employee.passport.UniquePassport;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@UniquePassport
@UniqueEmploymentContract
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "error.validation.employee.passport.empty")
    @Column(
            name = "passpot",
            length = 10,
            nullable = false
    )
    private String passport;

    @NotEmpty(message = "error.validation.employee.employmentContract.empty")
    @Column(
            name = "employment_contract",
            nullable = false
    )
    private String employmentContract;

    @Column(
            name = "dismissed",
            nullable = false
    )
    private boolean dismissed;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmploymentContract() {
        return employmentContract;
    }

    public void setEmploymentContract(String employmentContract) {
        this.employmentContract = employmentContract;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }
}
