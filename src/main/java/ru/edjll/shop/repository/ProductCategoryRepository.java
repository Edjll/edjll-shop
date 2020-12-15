package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.edjll.shop.domain.ProductCategory;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> getCategoriesByIdNot(Long id);

    List<ProductCategory> getCategoriesByParentId(Long id);

    List<ProductCategory> getCategoriesByParentNull();

    List<ProductCategory> getCategoryByParentIdInAndIdNot(Collection<Long> parent_id, Long id);

    ProductCategory getProductCategoryByName(String name);

    Page<ProductCategory> findAllByNameContainsIgnoreCase(String searchParam, Pageable pageable);

    Page<ProductCategory> findAllByChildrenIsNull(Pageable pageable);

    @Query(value = "call allChildrenCategory(?1)", nativeQuery = true)
    List<ProductCategory> findAllChildren(Long id);

    Page<ProductCategory> getAllByNameContainsIgnoreCase(String searchParam, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from ProductCategory pd where pd.id = ?1")
    void deleteById(Long id);
}
