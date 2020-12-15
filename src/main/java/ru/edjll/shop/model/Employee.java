package ru.edjll.shop.model;

public class Employee {

    private Long id;

    private Long user;

    private String passport;

    private String employmentContract;

    private Integer role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
