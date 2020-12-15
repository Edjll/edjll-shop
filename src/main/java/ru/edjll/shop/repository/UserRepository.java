package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select cast(registration_date as date) as castDate, count(registration_date) " +
                   "from user " +
                   "group by castDate " +
                   "having castDate >= ?1 and castDate <= ?2 " +
                   "order by castDate", nativeQuery = true)
    List<Object> getRegistrationUsersByDates(LocalDate dateStart, LocalDate dateEnd);

    Page<User> getAllByEmployeeIsNull(Pageable pageable);

    Page<User> getAllByEmployeeIsNullAndEmailContainsIgnoreCase(Pageable pageable, String email);

    @Query(value = "select u from User u left join u.employee on u.employee is null")
    List<User> getAllOnlyUser();
}
