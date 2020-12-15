package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Value;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.repository.ValueRepository;

import java.util.List;

@Service
public class ValueService {
    @Autowired
    private ValueRepository valueRepository;

    public Value save(Attribute attribute) {
        Value valueDomain = valueRepository.findByValue(attribute.getValue());
        if (valueDomain == null) {
            valueDomain = new Value();
            valueDomain.setValue(attribute.getValue());
            valueDomain.setUnit(attribute.getUnit());
            valueDomain = valueRepository.save(valueDomain);
        }
        return valueDomain;
    }

    public void deleteById(Value value) {
        valueRepository.deleteById(value.getId());
    }

    public Page<ru.edjll.shop.model.product.Value> getAllValuePage(Pageable pageable, String searchParam) {
        if (searchParam.length() > 0) {
            return valueRepository.findAllByValueContainsIgnoreCase(searchParam, pageable).map(ru.edjll.shop.model.product.Value::new);
        }
        return valueRepository.findAll(pageable).map(ru.edjll.shop.model.product.Value::new);
    }

    public List<ru.edjll.shop.model.product.Value> getAllValueModelByAttribute(Long id) {
        return valueRepository.getAllValueModelByAttribute(id);
    }

    public List<Value> getValuesWithOneAttributeValue(Long id) {
        return valueRepository.getValuesWithOneAttributeValue(id);
    }
}
