package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Sale;
import ru.edjll.shop.domain.SaleProduct;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<ru.edjll.shop.domain.Sale, Long> {

    @Query("select new ru.edjll.shop.model.Sale(s.date, count(s.date)) from Sale s where s.date >= :dateStart and s.date <= :dateEnd group by s.date")
    List<ru.edjll.shop.model.Sale> getSaleDate(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);

    Sale getBySaleProducts(SaleProduct saleProduct);

    Page<Sale> getAllByUserId(Long id, Pageable pageable);

    @Query(value =  "select cast(date as date) as date, sum(cost) as sum " +
            "from sale join sale_product sp " +
            "on sale.id = sp.sale_id " +
            "and sale.date >= :dateStart " +
            "and sale.date <= :dateEnd " +
            "group by cast(date as date)", nativeQuery = true)
    List<Object> getStatistics(@Param("dateStart") LocalDateTime start, @Param("dateEnd") LocalDateTime end);
}
