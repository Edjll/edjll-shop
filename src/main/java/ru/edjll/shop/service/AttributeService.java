package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Attribute;
import ru.edjll.shop.domain.AttributeCategory;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.repository.AttributeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ValueService valueService;

    public Attribute save(ru.edjll.shop.model.Attribute attribute, AttributeCategory attributeCategory) {
        Attribute attributeDomain = attributeRepository.findByName(attribute.getName());
        if (attributeDomain == null) {
            attributeDomain = new Attribute();
        }
        attributeDomain.setName(attribute.getName());
        attributeDomain.setDescription(attribute.getDescription());
        attributeDomain.setCategory(attributeCategory);
        attributeDomain = attributeRepository.save(attributeDomain);
        return attributeDomain;
    }

    public Attribute getAttributeByName(String name) {
        return attributeRepository.getAttributeByName(name);
    }

    public Page<Attribute> getAllAttributesByAttributeCategory(Pageable pageable, Long attributeCategoryId) {
        return attributeRepository.findByCategory(attributeCategoryId, pageable);
    }

    public Set<ru.edjll.shop.model.Attribute> getAttributesByProductCategory(Long id) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);
        Set<ru.edjll.shop.model.Attribute> attributes = new HashSet<>();
        while (productCategory != null) {
            attributes.addAll(productCategory.getAttributes()
                    .stream()
                    .filter(attribute -> attributes.stream().noneMatch(a -> a.getId().equals(attribute.getId())))
                    .map(ru.edjll.shop.model.Attribute::new)
                    .collect(Collectors.toSet()));
            productCategory = productCategory.getParent();
        }
        return attributes;
    }

    public Set<ru.edjll.shop.model.Attribute> getAttributesByProductCategoryWithoutYourself(Long id) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);
        if (productCategory.getParent() == null) return new HashSet<>();
        return getAttributesByProductCategory(productCategory.getParent().getId());
    }

    public Set<ru.edjll.shop.model.Attribute> getAttributesByProductCategoryWithValue(Long id) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);
        Set<ru.edjll.shop.model.Attribute> attributes = new HashSet<>();
        while (productCategory != null) {
            Set<ru.edjll.shop.model.Attribute> attr = productCategory.getAttributes()
                    .stream()
                    .filter(attribute -> attributes.stream().noneMatch(a -> a.getId().equals(attribute.getId())))
                    .map(att -> {
                        ru.edjll.shop.model.Attribute a = new ru.edjll.shop.model.Attribute(att);
                        a.setValues(new HashSet<>(valueService.getAllValueModelByAttribute(a.getId())));
                        return a;
                    })
                    .collect(Collectors.toSet());

            attributes.addAll(attr);

            productCategory = productCategory.getParent();
        }
        return attributes;
    }

    public Attribute getAttributeById(Long id) {
        return attributeRepository.getOne(id);
    }

    public void deleteById(Attribute attribute) {
        attributeRepository.deleteById(attribute.getId());
    }

    public List<Attribute> getAttributesWithOneAttributeValue(Long id) {
        return attributeRepository.getAttributesWithOneAttributeValue(id);
    }

    public Page<ru.edjll.shop.model.product.category.Attribute> getAllAttributeNamePage(Pageable pageable, List<Long> ids, String searchParam) {
        if (ids.size() > 0) {
            if (searchParam.length() > 0) {
                return attributeRepository.findAllByNameContainsIgnoreCaseAndIdNotIn(searchParam, ids, pageable)
                        .map(ru.edjll.shop.model.product.category.Attribute::new);
            } else {
                return attributeRepository.findAllByIdNotIn(ids, pageable)
                        .map(ru.edjll.shop.model.product.category.Attribute::new);
            }
        } else {
            if (searchParam.length() > 0) {
                return attributeRepository.findAllByNameContainsIgnoreCase(searchParam, pageable)
                        .map(ru.edjll.shop.model.product.category.Attribute::new);
            } else {
                return attributeRepository.findAll(pageable)
                        .map(ru.edjll.shop.model.product.category.Attribute::new);
            }
        }
    }

    public Page<ru.edjll.shop.model.product.category.Attribute> getAllAttributePage(Pageable pageable, List<String> attributes, String searchParam, String tracking) {
        Page<Attribute> attributePage;
        if (attributes.size() > 0) {
            if (searchParam.length() > 0) {
                if (tracking.length() > 0 && !tracking.equals("null")) {
                    attributePage = attributeRepository.findAllByNameContainsIgnoreCaseAndCategoryNameAndNameNotIn(searchParam, tracking, attributes.stream().map(s -> s.substring(1, s.length() - 1)).collect(Collectors.toList()), pageable);
                } else {
                    attributePage = attributeRepository.findAllByNameContainsIgnoreCaseAndNameNotIn(searchParam, attributes.stream().map(s -> s.substring(1, s.length() - 1)).collect(Collectors.toList()), pageable);
                }
            } else {
                if (tracking.length() > 0 && !tracking.equals("null")) {
                    attributePage = attributeRepository.findAllByCategoryNameAndNameNotIn(tracking, attributes.stream().map(s -> s.substring(1, s.length() - 1)).collect(Collectors.toList()), pageable);
                } else {
                    attributePage = attributeRepository.findAllByNameNotIn(attributes.stream().map(s -> s.substring(1, s.length() - 1)).collect(Collectors.toList()), pageable);
                }
            }
        } else {
            if (searchParam.length() > 0) {
                if (tracking.length() > 0 && !tracking.equals("null")) {
                    attributePage = attributeRepository.findAllByNameContainsIgnoreCaseAndCategoryName(searchParam, tracking, pageable);
                } else {
                    attributePage = attributeRepository.findAllByNameContainsIgnoreCase(searchParam, pageable);
                }
            } else {
                if (tracking.length() > 0 && !tracking.equals("null")) {
                    attributePage = attributeRepository.findAllByCategoryName(tracking, pageable);
                } else {
                    attributePage = attributeRepository.findAll(pageable);
                }
            }
        }
        return attributePage.map(attribute -> {
            ru.edjll.shop.model.product.category.Attribute attributeModel = new ru.edjll.shop.model.product.category.Attribute();
            attributeModel.setId(attribute.getId());
            attributeModel.setName(attribute.getName());
            return attributeModel;
        });
    }
}
