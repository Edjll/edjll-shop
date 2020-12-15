package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.Employee;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.service.EmployeeService;

import java.util.Map;

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/employee/add")
    public ResponseEntity<Map<String, String>> addEmployee(
            @RequestPart(name = "employee") Employee employee,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        employeeService.save(employee);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/employee/update")
    public ResponseEntity<Map<String, String>> updateEmployee(
            @RequestPart(name = "employee") Employee employee,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        employeeService.update(employee);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/employee/dismiss")
    public ResponseEntity<Map<String, String>> deleteEmployee(@RequestPart(name = "employee") IdWrapper idWrapper) {
        employeeService.delete(idWrapper.getId());
        return ResponseEntity.ok().body(null);
    }
}
