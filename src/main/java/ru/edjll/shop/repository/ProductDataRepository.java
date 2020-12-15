package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.domain.ProductData;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductDataRepository extends JpaRepository<ProductData, Long> {
    Page<ProductData> findAllByNameContainsIgnoreCase(String param, Pageable pageable);

    ProductData getProductDataByName(String name);

    Page<ProductData> findAllByNameContainsIgnoreCaseAndIdNotIn(String param, List<Long> id, Pageable pageable);

    Page<ProductData> getAllByIdNotIn(List<Long> id, Pageable pageable);

    @Query("select pd " +
            "from ProductData pd " +
            "where pd.promotionProducts.size = 0 or pd.id in ( select pd.id from ProductData pd join pd.promotionProducts pp join pp.promotion ppp on ppp.dateEnd < ?1 )")
    Page<ProductData> findAllWithoutPromotions(Date date, Pageable pageable);

    @Query("select pd " +
            "from ProductData pd " +
            "where (pd.promotionProducts.size = 0 or pd.id in ( select pd.id from ProductData pd join pd.promotionProducts pp join pp.promotion ppp on ppp.dateEnd < ?1 )) and pd.name like (concat('%', ?2, '%'))")
    Page<ProductData> findAllByNameContainsIgnoreCaseWithoutPromotions(Date date, String searchParam, Pageable pageable);

    @Query("select pd " +
            "from ProductData pd left join pd.promotionProducts pp " +
            "where (pd.promotionProducts.size = 0 or pd.id in ( select pd.id from ProductData pd join pd.promotionProducts pp join pp.promotion ppp on ppp.dateEnd < ?1 )) and pd.id not in ?2")
    Page<ProductData> getAllByIdNotInWithoutPromotions(Date date, List<Long> id, Pageable pageable);

    @Query("select pd " +
            "from ProductData pd left join pd.promotionProducts pp " +
            "where (pd.promotionProducts.size = 0 or pd.id in ( select pd.id from ProductData pd join pd.promotionProducts pp join pp.promotion ppp on ppp.dateEnd < ?1 )) and pd.id not in ?3 and pd.name like (concat('%', ?2, '%'))")
    Page<ProductData> findAllByNameContainsIgnoreCaseAndIdNotInWithoutPromotions(Date date, String searchParam, List<Long> id, Pageable pageable);

    List<ProductData> getAllByCategory(ProductCategory productCategory);

    @Query("select pd from ProductData pd join pd.promotionProducts ppd join ppd.promotion p on p.id = ?1")
    Page<ProductData> getAllByPromotion(Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from ProductData pd where pd.id = ?1")
    void deleteById(Long id);
}
