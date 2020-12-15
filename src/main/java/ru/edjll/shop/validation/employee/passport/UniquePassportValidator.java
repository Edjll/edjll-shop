package ru.edjll.shop.validation.employee.passport;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.domain.Employee;
import ru.edjll.shop.service.EmployeeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePassportValidator implements ConstraintValidator<UniquePassport, Employee> {

    @Autowired
    private EmployeeService employeeService;


    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext ctx) {
        Employee employeeDomain = employeeService.getEmployeeByPassport(employee.getPassport());
        if (employeeDomain == null || employeeDomain.getId().equals(employee.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.employee.passport.unique")
                .addPropertyNode("passport")
                .addConstraintViolation();
        return false;
    }
}
