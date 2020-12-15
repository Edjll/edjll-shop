package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Employee;
import ru.edjll.shop.domain.Role;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.repository.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    public Employee getEmployeeByPassport(String passport) {
        return employeeRepository.getEmployeeByPassport(passport);
    }

    public Employee getEmployeeByEmploymentContract(String employmentContract) {
        return employeeRepository.getEmployeeByEmploymentContract(employmentContract);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> getPageEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeByUserId(Long id) {
        return employeeRepository.getByUserId(id);
    }

    public void save(ru.edjll.shop.model.Employee employee) {
        Employee employeeDomain = new Employee();

        User user = userService.getUserById(employee.getUser());
        user.getRoles().clear();
        for (Role role : Role.values()) {
            if (role.ordinal() < employee.getRole()) {
                user.getRoles().add(role);
            }
        }
        userService.save(user);

        employeeDomain.setUser(user);
        employeeDomain.setPassport(employee.getPassport());
        employeeDomain.setEmploymentContract(employee.getEmploymentContract());
        employeeDomain.setDismissed(false);

        employeeRepository.save(employeeDomain);
    }

    public void update(ru.edjll.shop.model.Employee employee) {
        Employee employeeDomain = employeeRepository.getOne(employee.getId());

        User user = userService.getUserById(employeeDomain.getUser().getId());
        user.getRoles().clear();
        for (Role role : Role.values()) {
            if (role.ordinal() < employee.getRole()) {
                user.getRoles().add(role);
            }
        }
        userService.save(user);

        employeeDomain.setPassport(employee.getPassport());
        employeeDomain.setEmploymentContract(employee.getEmploymentContract());
        employeeDomain.setDismissed(false);

        employeeRepository.save(employeeDomain);
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.getOne(id);

        User user = userService.getUserById(employee.getUser().getId());
        user.getRoles().clear();
        user.getRoles().add(Role.USER);
        userService.save(user);

        employee.setDismissed(true);
        employeeRepository.save(employee);
    }
}
