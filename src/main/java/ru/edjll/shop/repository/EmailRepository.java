package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Email getByEmail(String email);
}
