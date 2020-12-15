package ru.edjll.shop.validation.employee.employmentContract;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.domain.Employee;
import ru.edjll.shop.service.EmployeeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmploymentContractValidator implements ConstraintValidator<UniqueEmploymentContract, Employee> {

    @Autowired
    private EmployeeService employeeService;


    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext ctx) {
        Employee employeeDomain = employeeService.getEmployeeByEmploymentContract(employee.getEmploymentContract());
        if (employeeDomain == null || employeeDomain.getId().equals(employee.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.employee.employmentContract.unique")
                .addPropertyNode("employmentContract")
                .addConstraintViolation();
        return false;
    }
}
