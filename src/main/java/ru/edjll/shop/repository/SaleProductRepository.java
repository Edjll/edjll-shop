package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.SaleProduct;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {
    @Query(value = "select product_data_id, count(product_data_id) " +
                   "from product p join (sale_product sp join sale s " +
                   "on s.id = sp.sale_id)" +
                   "on p.id = sp.product_id " +
                   "where s.date >= ?1 and s.date < ?2 " +
                   "group by product_data_id", nativeQuery = true)
    List<Object> getProductSalesByDates(LocalDate dateStart, LocalDate dateEnd);

    @Query(value = "select cast(s.date as date) as castDate, count(s.date) " +
                   "from product p join (sale_product sp join sale s " +
                   "on sp.sale_id = s.id)" +
                   "on p.id = sp.id " +
                   "where s.date >= ?2 and s.date < ?3 and p.product_data_id =?1 " +
                   "group by castDate", nativeQuery = true)
    List<Object> getProductStatistics(Long id, LocalDate dateStart, LocalDate dateEnd);

    List<SaleProduct> getAllBySaleId(Long id);
}
