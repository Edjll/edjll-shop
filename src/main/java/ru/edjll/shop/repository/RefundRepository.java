package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Refund;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    @Query(value = "select product_data_id, count(product_data_id) " +
                   "from refund r join (product p join product_data pd " +
                   "on pd.id = p.product_data_id) " +
                   "on r.product_id = p.id " +
                   "group by product_data_id", nativeQuery = true)
    List<Object> getProductsRefund();

    @Query("select r from Refund r join r.saleProduct rsp join rsp.sale s join s.user u on u.id = ?1")
    Page<Refund> getAllByUser(Long id, Pageable pageable);
}
