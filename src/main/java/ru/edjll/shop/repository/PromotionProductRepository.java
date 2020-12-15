package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.PromotionProduct;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {

    PromotionProduct getAllByProductDataId(Long id);
}
