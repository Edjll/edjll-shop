package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select product_data_id, cast(avg(rating) as decimal(5,1)) " +
                   "from product_data pd join review r " +
                   "on pd.id = r.product_data_id " +
                   "group by product_data_id", nativeQuery = true)
    List<Object> getProductsRating();

    Page<Review> getAllByUserId(Long id, Pageable pageable);

    Review getByUserIdAndProductDataId(Long user, Long productData);
}
