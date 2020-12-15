package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Attribute;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.domain.Value;
import ru.edjll.shop.model.AttributeCategory;
import ru.edjll.shop.model.CategoryAttribute;
import ru.edjll.shop.repository.AttributeCategoryRepository;

import java.util.*;

@Service
public class AttributeCategoryService {
    @Autowired
    private AttributeCategoryRepository attributeCategoryRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    public ru.edjll.shop.domain.AttributeCategory save(String category) {
        ru.edjll.shop.domain.AttributeCategory attributeCategoryDomain = attributeCategoryRepository.findByName(category);
        if (attributeCategoryDomain == null) {
            attributeCategoryDomain = new ru.edjll.shop.domain.AttributeCategory();
            attributeCategoryDomain.setName(category);
            attributeCategoryDomain = attributeCategoryRepository.save(attributeCategoryDomain);
        }
        return attributeCategoryDomain;
    }

    public void deleteById(Long id) {
        attributeCategoryRepository.deleteById(id);
    }

    public ru.edjll.shop.domain.AttributeCategory getAttributeCategoryByName(String name) {
        return attributeCategoryRepository.getAttributeCategoryByName(name);
    }

    public Page<CategoryAttribute> getAttributeCategoryPage(Pageable pageable, String searchParam) {
        Page<ru.edjll.shop.domain.AttributeCategory> attributeCategories;
        if (searchParam.length() > 0) attributeCategories = attributeCategoryRepository.findAllByNameContainsIgnoreCase(searchParam, pageable);
        else attributeCategories = attributeCategoryRepository.findAll(pageable);

        return attributeCategories.map(attributeCategory -> {
            CategoryAttribute categoryAttribute = new CategoryAttribute();
            categoryAttribute.setId(attributeCategory.getId());
            categoryAttribute.setName(attributeCategory.getName());
            return categoryAttribute;
        });
    }

    public List<AttributeCategory> getUseAttributeCategories(Long id) {
        return attributeCategoryRepository.getUseAttributeCategories(id);
    }

    public List<ru.edjll.shop.domain.AttributeCategory> getAttributeCategoriesByProductDataId(Long id) {
        return attributeCategoryRepository.getAttributeCategoriesByProductDataId(id);
    }

    public Map<Long, List<Value>> getAttributesAndValues(Long id) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);

        Set<Attribute> attributes = productCategory.getAttributes();

        Map<Long, List<Value>> attributesAndValues = new HashMap<>();
        attributes.forEach(attribute -> {
            List<Value> values = new ArrayList<>();
            attribute.getAttributeValues().forEach(attributeValue -> {
                values.add(attributeValue.getValue());
            });
            attributesAndValues.put(attribute.getId(), values);
        });

        return attributesAndValues;
    }
}
