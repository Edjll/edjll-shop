package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("select p " +
//            "from Product p " +
//            "where p.productData.")
//    Product getFirstByProductDataId(Long id);

    @Procedure(procedureName = "maxCount")
    Long getMaxCountProduct(@Param("productId") Long id);

    @Query(value = "call sellableProducts(:productId)", nativeQuery = true)
    List<Product> sellableProducts(@Param("productId") Long id);
}
