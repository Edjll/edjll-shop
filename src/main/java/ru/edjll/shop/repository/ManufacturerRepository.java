package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Manufacturer getManufacturerByName(String name);

    @Query(value = "select new ru.edjll.shop.model.Manufacturer(m.id, m.name) " +
                   "from Manufacturer m")
    Page<ru.edjll.shop.model.Manufacturer> findAllModel(Pageable pageable);
}
