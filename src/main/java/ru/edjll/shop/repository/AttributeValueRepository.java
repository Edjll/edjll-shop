package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Attribute;
import ru.edjll.shop.domain.AttributeValue;
import ru.edjll.shop.domain.Value;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    AttributeValue findByAttributeAndValue(Attribute attribute, Value value);

    @Query("select av " +
            "from ProductData pd join pd.attributes av " +
            "where pd.id = ?1 and av.products.size = 1")
    List<AttributeValue> getAttributeValuesWithOneProduct(Long id);

    AttributeValue getAttributeValueByAttributeIdAndValueId(Long attribute, Long value);
}
