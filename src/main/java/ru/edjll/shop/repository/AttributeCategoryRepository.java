package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.AttributeCategory;

import java.util.List;

@Repository
public interface AttributeCategoryRepository extends JpaRepository<AttributeCategory, Long> {
    AttributeCategory findByName(String Name);

    AttributeCategory getAttributeCategoryByName(String name);

    Page<AttributeCategory> findAllByIdNotIn(List<Long> id, Pageable pageable);

    @Query("select new ru.edjll.shop.model.AttributeCategory(ac.id, ac.name) " +
            "from ProductData pd join pd.attributes av join av.attribute a join a.category ac " +
            "where pd.id = ?1")
    List<ru.edjll.shop.model.AttributeCategory> getUseAttributeCategories(Long productData);

    @Query("select new AttributeCategory(pda.category.id, pda.category.name) from ProductData pd join pd.attributes pdav join pdav.attribute pda join pda.category where pd.id = ?1 group by pda.category")
    List<AttributeCategory> getAttributeCategoriesByProductDataId(Long id);

    Page<AttributeCategory> findAllByNameContainsIgnoreCase(String param, Pageable pageable);

    Page<AttributeCategory> findAllByNameContainsIgnoreCaseAndNameNotIn(String param, List<String> names, Pageable pageable);

    Page<AttributeCategory> getAllByNameNotIn(List<String> id, Pageable pageable);
}
