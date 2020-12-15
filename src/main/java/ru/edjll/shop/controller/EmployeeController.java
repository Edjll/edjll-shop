package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Employee;
import ru.edjll.shop.domain.Role;
import ru.edjll.shop.service.EmployeeService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/employee/add")
    public String getAddEmployeePage(Map<String, Object> model) {
        model.put("roles", Arrays.stream(Role.values()).skip(1).collect(Collectors.toList()));
        return "admin/employee/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/employee/update/{id}")
    public String getUpdateEmployeePage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Employee employee = employeeService.getEmployeeById(id);

        model.put("roles", Arrays.stream(Role.values())
                .skip(1)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparingInt(Enum::ordinal))
                .collect(Collectors.toList()));
        model.put("employee", employee);
        return "admin/employee/update";
    }
}
