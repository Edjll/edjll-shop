package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.Category;
import ru.edjll.shop.repository.ProductCategoryRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private ProductDataService productDataService;

    public ProductCategory saveCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public Page<ru.edjll.shop.model.shop.ProductCategory> getAllProductCategoriesModelShop(String searchParam, Pageable pageable) {
        if (searchParam.isEmpty()) {
            return productCategoryRepository.findAll(pageable)
                    .map(ru.edjll.shop.model.shop.ProductCategory::new);
        } else {
            return productCategoryRepository.getAllByNameContainsIgnoreCase(searchParam, pageable)
                    .map(ru.edjll.shop.model.shop.ProductCategory::new);
        }
    }

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    public List<ProductCategory> getAllChildrenCategory(Long id) {
        return productCategoryRepository.findAllChildren(id);
    }

    public Page<ru.edjll.shop.model.shop.ProductCategory> getProductCategoryShopModel(Long id, String searchParam, Pageable pageable) {
        List<ru.edjll.shop.model.shop.ProductCategory> categories = getAllChildrenCategory(id).stream()
                .map(ru.edjll.shop.model.shop.ProductCategory::new)
                .collect(Collectors.toList());

        if (!searchParam.isEmpty()) {
            categories = categories.stream()
                    .filter(productCategory -> productCategory.getName().toUpperCase().contains(searchParam.toUpperCase()))
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(categories, pageable, 0);
    }

    public List<ProductCategory> getProductCategoryChildren(Long id) {
        return getAllChildrenCategory(id);
    }

    public boolean deleteCategoryById(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        List<ProductCategory> childCategories = productCategory.getChildren();
        if (productCategory.getProducts().isEmpty()) {
            if (productCategory.getParent() == null) {
                childCategories.forEach(childCategory -> {
                    childCategory.setParent(null);
                    productCategoryRepository.save(childCategory);
                });
            } else {
                ProductCategory finalProductCategory = productCategory;
                childCategories.forEach(childCategory -> {
                    childCategory.setParent(finalProductCategory.getParent());
                    productCategoryRepository.save(childCategory);
                });
            }
            productCategory.setChildren(null);
            productCategory.setParent(null);
            productCategory = productCategoryRepository.save(productCategory);

            productCategory.getAttributes().forEach(attribute -> {
                if (attribute.getProductCategories().size() == 1 && attribute.getAttributeValues().size() == 0) {
                    attributeService.deleteById(attribute);
                }
            });
            productCategory.setAttributes(null);
            productCategoryRepository.save(productCategory);

            productCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductCategory> getCategoriesByIdNot(Long id) {
        List<ProductCategory> categories = productCategoryRepository.getCategoriesByIdNot(id);
        return categories;
    }

    public List<ProductCategory> getChildCategories(Long id) {
        return productCategoryRepository.getCategoriesByParentId(id);
    }

    public List<ProductCategory> getCategoriesWithoutParent() {
        return productCategoryRepository.getCategoriesByParentNull();
    }

    public void updateChildCategories(List<ProductCategory> children, ProductCategory parent) {
        children.stream()
                .filter(Objects::nonNull)
                .forEach(category -> {
                    category.setParent(parent);
                    saveCategory(category);
                });
    }

    public Map<String, String> updateCategory(ru.edjll.shop.model.ProductCategory productCategory) {
        ProductCategory productCategoryDomain = productCategoryRepository.getOne(productCategory.getId());
        productCategoryDomain.setName(productCategory.getName());
        productCategoryDomain.setDescription(productCategory.getDescription());

        Map<String, String> nonValid = new HashMap<>();

        final int[] index = {0};
        productCategory.getAttributes().forEach(attr -> {
            Attribute attribute = attributeService.getAttributeByName(attr.getName());
            if (attribute != null) {
                productCategoryDomain.getProducts().forEach(prod -> {
                    if (prod.getAttributes().stream()
                            .map(AttributeValue::getAttribute)
                            .noneMatch(a -> a.getId().equals(attribute.getId()))
                    ) {
                        nonValid.put("category.attributes.wrapper[" + index[0] + "].name", "Характеристики нет в продуктах");
                    }
                });
            } else {
                if (!productCategoryDomain.getProducts().isEmpty()) {
                    nonValid.put("category.attributes.wrapper[" + index[0] + "].name", "Характеристики нет в продуктах");
                }
            }
            index[0]++;
        });

        productCategory.getVertexes().forEach(vertex -> {
            if (!checkingValidityAttributes(vertex.getId(), productCategory.getVertexes(), productCategory)) {
                nonValid.put("error" + vertex.getId(), "Невозможно перепривязать характеристику '" + vertex.getName() + "', т.к. продукты не содержат родительских атрибутов");
            }
        });

        if (!nonValid.isEmpty()) {
            return nonValid;
        }

        Set<Attribute> attributesOld = productCategoryDomain.getAttributes();
        Set<Attribute> attributes = new HashSet<>();

        if (productCategory.getAttributes() != null) {
            attributes = productCategory.getAttributes().stream().map(attributeModel -> {
                Attribute attribute = attributeService.getAttributeByName(attributeModel.getName());
                if (attribute == null) {
                    attribute = new Attribute();
                    attribute.setName(attributeModel.getName());
                    attribute.setDescription(attributeModel.getDescription());
                }
                return attribute;
            }).collect(Collectors.toSet());

            productCategoryDomain.setAttributes(attributes);
        }

        productCategoryRepository.save(productCategoryDomain);

        Set<Attribute> tmp = attributes;

        attributesOld.stream()
                .filter(item -> tmp.stream()
                        .noneMatch(a -> a.getId().equals(item.getId())))
                .forEach(item -> {
                    attributeService.deleteById(item);
                });

        productCategory.getVertexes().forEach(category -> {
            updateProductCategoryParent(category, null);
        });

        return nonValid;
    }

    public boolean checkingValidityAttributes(Long id, List<Category> vertexes, ru.edjll.shop.model.ProductCategory pc) {
        if (id == -1) return true;
        ProductCategory productCategory = productCategoryRepository.getOne(id);
        Set<Attribute> productCategoryAttributes = productCategory.getAttributes();
        ProductCategory parent = null;
        AtomicBoolean newAttributes = new AtomicBoolean(false);
        Category parentCategory = vertexes
                .stream()
                .filter(c -> c.getId().equals(productCategory.getId()))
                .findFirst()
                .orElse(null);

        if (parentCategory != null && parentCategory.getParent() != null) {
            if (parentCategory.getParent() != -1 && !parentCategory.getParent().equals(pc.getId())) {
                parent = productCategoryRepository.getOne(parentCategory.getParent());
            } else {
                pc.getAttributes().forEach(attributeModel -> {
                    Attribute attribute = attributeService.getAttributeByName(attributeModel.getName());
                    if (attribute == null) {
                        newAttributes.set(true);
                    } else {
                        productCategoryAttributes.add(attribute);
                    }
                });
            }
        }

        while(parent != null) {
            productCategoryAttributes.addAll(parent.getAttributes());
            Category finalParentCategory = parentCategory;
            parentCategory = vertexes
                    .stream()
                    .filter(c -> c.getId().equals(finalParentCategory.getParent()))
                    .findFirst()
                    .orElse(null);
            if (parentCategory != null && parentCategory.getParent() != null) {
                if (parentCategory.getParent() != -1 && !parentCategory.getParent().equals(pc.getId())) {
                    parent = productCategoryRepository.getOne(parentCategory.getParent());
                } else {
                    pc.getAttributes().forEach(attributeModel -> {
                        Attribute attribute = attributeService.getAttributeByName(attributeModel.getName());
                        if (attribute == null) {
                            newAttributes.set(true);
                        } else {
                            productCategoryAttributes.add(attribute);
                        }
                    });
                    parent = null;
                }
            } else {
                parent = null;
            }
        }

        List<ProductData> productDataList = productDataService.getProductsByCategory(productCategory);
        if (!productDataList.isEmpty() && newAttributes.get()) return false;

        for (ProductData productData : productDataList) {
            Set<AttributeValue> attributeValues = productData.getAttributes();
            for (Attribute attribute : productCategoryAttributes) {
                if (attributeValues.stream().noneMatch(attributeValue -> attributeValue.getAttribute().getName().equals(attribute.getName()))) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<ProductCategory> getNeighboringAndChildCategories(ProductCategory productCategory) {
        if (productCategory.getParent() == null) {
            return productCategoryRepository.getCategoriesByParentId(productCategory.getId());
        } else {
            return productCategoryRepository.getCategoryByParentIdInAndIdNot(Arrays.asList(productCategory.getId(), productCategory.getParent().getId()), productCategory.getId());
        }
    }

    public Page<ProductCategory> getPageCategories(Pageable pageable) {
        return  productCategoryRepository.findAll(pageable);
    }

    public List<Category> getAllCategoriesModel() {
        List<ProductCategory> categories = productCategoryRepository.getCategoriesByParentNull();
        return categories.stream().map(category -> {
            Category categoryModel = new Category();
            categoryModel.setId(category.getId());
            categoryModel.setName(category.getName());
            categoryModel.setConvertedChildren(category.getChildren());
            categoryModel.setEditable(category.getProducts().isEmpty());
            return categoryModel;
        }).collect(Collectors.toList());
    }

    public Map<String, String> saveProductCategory(ru.edjll.shop.model.ProductCategory productCategory) {

        Map<String, String> nonValid = new HashMap<>();
        productCategory.getVertexes().forEach(vertex -> {
            if (!checkingValidityAttributes(vertex.getId(), productCategory.getVertexes(), productCategory)) {
                nonValid.put("error" + vertex.getId(), "Невозможно перепривязать характеристику '" + vertex.getName() + "', т.к. продукты не содержат родительских атрибутов");
            }
        });

        if (!nonValid.isEmpty()) return nonValid;

        ProductCategory productCategoryDomain = new ProductCategory();
        productCategoryDomain.setId(productCategory.getId());
        productCategoryDomain.setName(productCategory.getName());
        productCategoryDomain.setDescription(productCategory.getDescription());

        if (productCategory.getAttributes() != null) {
            Set<Attribute> attributes = productCategory.getAttributes().stream().map(attributeModel -> {
                Attribute attribute = attributeService.getAttributeByName(attributeModel.getName());
                if (attribute == null) {
                    attribute = new Attribute();
                    attribute.setName(attributeModel.getName());
                    attribute.setDescription(attributeModel.getDescription());
                }
                return attribute;
            }).collect(Collectors.toSet());

            productCategoryDomain.setAttributes(attributes);
        }

        ProductCategory categorySaved = productCategoryRepository.save(productCategoryDomain);
        productCategory.getVertexes().get(0).setId(categorySaved.getId());

        productCategory.getVertexes().forEach(category -> {
            updateProductCategoryParent(category, categorySaved);
        });

        return nonValid;
    }

    public void updateProductCategoryParent(Category category, ProductCategory categorySaved) {
        ProductCategory productCategory = productCategoryRepository.getOne(category.getId());
        if (category.getParent() == null) {
            productCategory.setParent(null);
        } else if (category.getParent() == -1) {
            productCategory.setParent(categorySaved);
        } else {
            ProductCategory categoryParent = productCategoryRepository.getOne(category.getParent());
            productCategory.setParent(categoryParent);
        }
        productCategoryRepository.save(productCategory);
    }

    public ProductCategory getProductCategoryByName(String name) {
        return productCategoryRepository.getProductCategoryByName(name);
    }

    public Page<Category> getCategoriesWithoutChildren(Pageable pageable, String searchParam) {
        Page<ProductCategory> productCategories;
        if (searchParam.length() > 0) {
            productCategories = productCategoryRepository.findAllByNameContainsIgnoreCase(searchParam, pageable);
        }
        else {
            productCategories = productCategoryRepository.findAllByChildrenIsNull(pageable);
        }
        return productCategories.map(productCategory -> {
            Category category = new Category();
            category.setId(productCategory.getId());
            category.setName(productCategory.getName());
            return category;
        });
    }
}
