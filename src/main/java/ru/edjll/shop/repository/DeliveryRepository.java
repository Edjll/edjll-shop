package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("select d.address " +
            "from User u join u.sales s join s.saleProducts sp join sp.delivery d " +
            "where u.id = ?2 " +
            "group by d.address " +
            "having lower(d.address) like lower(concat('%', ?1, '%'))")
    Page<String> getAllByUserWithoutCase(String searchParam, Long id, Pageable pageable);

    @Query("select d.address " +
            "from User u join u.sales s join s.saleProducts sp join sp.delivery d " +
            "where u.id = ?1 " +
            "group by d.address")
    Page<String> getAllByUser(Long id, Pageable pageable);
}
