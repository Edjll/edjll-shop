package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Basket;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> getAllByUserId(Long id);

    Basket getByProductDataId(Long id);
}
