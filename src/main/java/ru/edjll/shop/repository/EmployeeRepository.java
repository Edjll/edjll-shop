package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee getEmployeeByPassport(String passport);

    Employee getEmployeeByEmploymentContract(String employmentContract);

    Employee getByUserId(Long id);
}
