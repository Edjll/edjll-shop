package ru.edjll.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Value;

import java.util.List;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {

    Value findByValue(String value);

    Page<Value> findAllByValueContainsIgnoreCase(String searchParam, Pageable pageable);

    @Query("select new ru.edjll.shop.model.product.Value(v) from Attribute a join a.attributeValues av join av.value v on a.id = ?1")
    List<ru.edjll.shop.model.product.Value> getAllValueModelByAttribute(Long id);

    Value getByValue(String value);

    @Query("select v from Value v join v.attributeValues av on av.id = ?1 and v.attributeValues.size = 1")
    List<Value> getValuesWithOneAttributeValue(Long id);
}
