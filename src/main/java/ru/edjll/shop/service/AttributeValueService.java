package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Attribute;
import ru.edjll.shop.domain.AttributeValue;
import ru.edjll.shop.domain.Value;
import ru.edjll.shop.repository.AttributeValueRepository;

import java.util.List;

@Service
public class AttributeValueService {
    @Autowired
    private AttributeValueRepository attributeValueRepository;

    public AttributeValue save(Attribute attribute, Value value) {
        AttributeValue attributeValueDomain = attributeValueRepository.findByAttributeAndValue(attribute, value);
        if (attributeValueDomain == null) {
            attributeValueDomain = new AttributeValue();
            attributeValueDomain.setAttribute(attribute);
            attributeValueDomain.setValue(value);
            attributeValueDomain = attributeValueRepository.save(attributeValueDomain);
        }
        return attributeValueDomain;
    }

    public List<AttributeValue> getAttributeValuesWithOneProduct(Long id) {
        return attributeValueRepository.getAttributeValuesWithOneProduct(id);
    }

    public void deleteById(Long id) {
        attributeValueRepository.deleteById(id);
    }

    public AttributeValue getAttributeValueByAttributeAndValue(Long attribute, Long value) {
        return attributeValueRepository.getAttributeValueByAttributeIdAndValueId(attribute, value);
    }
}
