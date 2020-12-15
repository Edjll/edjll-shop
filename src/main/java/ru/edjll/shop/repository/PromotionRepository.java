package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion getPromotionByName(String name);
}
