package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Supply;
import ru.edjll.shop.model.SupplyProductData;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    @Query("select new ru.edjll.shop.model.SupplyProductData(count(sp.productData), sp.productData.name) from Supply s join s.products sp join sp.productData where s.id = ?1 group by sp.productData")
    List<SupplyProductData> getAllSupplyProductData(Long id);
}
