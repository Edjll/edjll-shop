package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City getCityByName(String name);
}
