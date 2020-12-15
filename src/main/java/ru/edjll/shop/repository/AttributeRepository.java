package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Attribute;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Attribute findByName(String Name);

    Attribute getAttributeByName(String name);

    @Query("select a " +
            "from Attribute a join a.category c " +
            "where c.id = ?1")
    Page<Attribute> findByCategory(Long id, Pageable pageable);

    Page<Attribute> findAllByNameContainsIgnoreCaseAndIdNotIn(String searchParam, List<Long> ids, Pageable pageable);

    Page<Attribute> findAllByIdNotIn(List<Long> ids, Pageable pageable);

    Page<Attribute> findAllByNameContainsIgnoreCase(String searchParam, Pageable pageable);

    Page<Attribute> findAllByNameContainsIgnoreCaseAndNameNotIn(String searchParam, List<String> names, Pageable pageable);

    Page<Attribute> findAllByNameContainsIgnoreCaseAndCategoryName(String searchParam, String category, Pageable pageable);

    Page<Attribute> findAllByNameContainsIgnoreCaseAndCategoryNameAndNameNotIn(String searchParam, String category, List<String> names, Pageable pageable);

    Page<Attribute> findAllByCategoryName(String category, Pageable pageable);

    Page<Attribute> findAllByCategoryNameAndNameNotIn(String category, List<String> names, Pageable pageable);

    Page<Attribute> findAllByNameNotIn(List<String> names, Pageable pageable);

    @Query("select a from Attribute a join a.attributeValues av on av.id = ?1 and a.attributeValues.size = 1")
    List<Attribute> getAttributesWithOneAttributeValue(Long id);
}
