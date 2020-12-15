package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.Search;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.repository.ProductDataRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductDataService {
    @Autowired
    private ProductDataRepository productDataRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private AttributeCategoryService attributeCategoryService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private ValueService valueService;

    @Autowired
    private AttributeValueService attributeValueService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionProductService promotionProductService;

    @Autowired
    private BasketService basketService;

    public List<ProductData> getAllProducts() {
        return productDataRepository.findAll();
    }

    public void save(ru.edjll.shop.model.ProductData productData, List<MultipartFile> images) {
        Set<AttributeValue> attributes = new HashSet<>();

        ProductData productDataDomain = new ProductData();

        productDataDomain.setId(productData.getId());
        productDataDomain.setName(productData.getName());
        productDataDomain.setDescription(productData.getDescription());
        productDataDomain.setCost(productData.getCost());
        productDataDomain.setShelfLife(productData.getShelfLife());

        Manufacturer manufacturer = manufacturerService.getManufacturerById(productData.getManufacturer());
        Country country = countryService.getCountryById(productData.getCountry());
        ProductCategory category = productCategoryService.getCategoryById(productData.getCategory());

        productDataDomain.setCountry(country);
        productDataDomain.setManufacturer(manufacturer);
        productDataDomain.setCategory(category);

        ProductData productDataDomainSaved = productDataRepository.save(productDataDomain);

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> imageService.saveProductDataImage(image, productDataDomain));
        }

        productData.getAttributes().forEach(attribute -> {
            AttributeCategory attributeCategoryDomain = attributeCategoryService.save(attribute.getCategory());
            Attribute attributeDomain = attributeService.save(attribute, attributeCategoryDomain);
            Value valueDomain = valueService.save(attribute);
            AttributeValue attributeValueDomain = attributeValueService.save(attributeDomain, valueDomain);
            attributes.add(attributeValueDomain);
        });

        productDataDomain.setAttributes(attributes);

        productDataRepository.save(productDataDomain);
    }

    public Page<ProductData> getPageProducts(Pageable pageable) {
        return productDataRepository.findAll(pageable);
    }

    public Double getMaxPrice(List<ProductDataCart> productDataCarts) {
        ProductDataCart productDataCart = productDataCarts.stream()
                .filter(pdc -> pdc.getMaxCount() != null && pdc.getMaxCount() > 0)
                .max(Comparator.comparing(ProductDataCart::getDiscountPrice)).orElse(null);
        if (productDataCart != null) {
            return productDataCart.getDiscountPrice();
        }
        return 0.0;
    }

    public List<ProductData> getAll() {
        return productDataRepository.findAll();
    }

    public List<ProductDataCart> search(Search search) {
        List<ProductData> productData;

        if (search.getCategory() != null) {
            List<ProductCategory> productCategories = productCategoryService.getProductCategoryChildren(search.getCategory());
            List<ProductData> tmp = new ArrayList<>();
            productCategories.forEach(productCategory -> tmp.addAll(
                    productCategory.getProducts())
            );
            productData = tmp;
        } else {
            productData = getAll();
        }

        List<ProductData> productDataByAttributes = getPageProductsByAttributes(search, productData);

        List<ProductDataCart> productDataCarts = getProductDataCart(productDataByAttributes);

        List<ProductDataCart> productDataCartsByCost = productDataCarts.stream().filter(productDataCart ->
            productDataCart.getMaxCount() != null
            && productDataCart.getMaxCount() > 0
            && productDataCart.getDiscountPrice() >= search.getMin()
            && (search.getMax() == null || productDataCart.getDiscountPrice() <= search.getMax())
        ).collect(Collectors.toList());

        return productDataCartsByCost;
    }

    public Page<ProductDataCart> search(Search search, Pageable pageable) {
        return new PageImpl<>(search(search), pageable, 0);
    }

    public Page<ProductDataCart> search(List<ProductDataCart> productDataCarts, Pageable pageable) {
        return new PageImpl<>(productDataCarts, pageable, productDataCarts.size() / pageable.getPageSize());
    }

    public List<ProductData> getPageProductsByAttributes(Search search, List<ProductData> productData) {
        if (search.getAttributes() != null && search.getAttributes().size() > 0) {
            final Set<ProductData>[] tmp = new Set[]{new HashSet<>(productData)};

            search.getAttributes()
                    .forEach(attributeSearch -> {
                        List<ru.edjll.shop.model.Value> as = attributeSearch.getValues().stream()
                                .filter(ru.edjll.shop.model.Value::isStatus)
                                .collect(Collectors.toList());

                        if (!as.isEmpty()) {
                            Set<ProductData> atmp = new HashSet<>();

                            as.forEach(value -> {
                                AttributeValue av = attributeValueService.getAttributeValueByAttributeAndValue(attributeSearch.getId(), value.getId());
                                atmp.addAll(tmp[0].stream()
                                        .filter(pd -> pd.getAttributes()
                                                .stream()
                                                .anyMatch(attrV -> attrV.getId().equals(av.getId())))
                                        .collect(Collectors.toList()));
                            });
                            tmp[0] = atmp;
                        }
                    });
            return new ArrayList<>(tmp[0]);
        }

        return productData;
    }

    public Page<ProductData> getPageProductsByPromotion(Long id, Pageable pageable) {
        return productDataRepository.getAllByPromotion(id, pageable);
    }

    public Page<ru.edjll.shop.model.rest.ProductData> getPageProductsSearch(Pageable pageable, String searchParam, List<Long> id) {
        Page<ProductData> productData;
        if (id == null || id.isEmpty()) {
            if (searchParam.isEmpty()) {
                productData = productDataRepository.findAll(pageable);
            } else {
                productData = productDataRepository.findAllByNameContainsIgnoreCase(searchParam, pageable);
            }
        } else {
            if (searchParam.isEmpty()) {
                productData = productDataRepository.getAllByIdNotIn(id, pageable);
            } else {
                productData = productDataRepository.findAllByNameContainsIgnoreCaseAndIdNotIn(searchParam, id, pageable);
            }
        }
        return productData.map(product -> {
            ru.edjll.shop.model.rest.ProductData productDataRest = new ru.edjll.shop.model.rest.ProductData();
            productDataRest.setId(product.getId());
            productDataRest.setName(product.getName());
            return productDataRest;
        });
    }

    public ProductData getProductDataByName(String name) {
        return productDataRepository.getProductDataByName(name);
    }

    public ProductData getProductDataById(Long id) {
        return productDataRepository.findById(id).orElse(null);
    }

    public Page<ProductDataCart> getProductDataCarts(Pageable pageable) {
        Page<ProductData> productData = getPageProducts(pageable);
        return getProductDataCartPage(productData);
    }

    public List<ProductDataCart> getProductDataCart(Collection<ProductData> productData) {
        return productData.stream().map(
                pd -> {
                    ProductDataCart productDataCart = new ProductDataCart();

                    productDataCart.setId(pd.getId());
                    productDataCart.setName(pd.getName());
                    productDataCart.setDescription(pd.getDescription());
                    Image image = imageService.getFirstImageByProductDataId(pd.getId());
                    if (image != null) {
                        productDataCart.setImage(image.getFilename());
                    }
                    productDataCart.setPrice(pd.getCost());
                    productDataCart.setMaxCount(productService.getProductMaxCount(pd.getId()));

                    if (pd.getReviews().size() > 0) {
                        Integer ratingAll = pd.getReviews().stream()
                                .filter(review -> review.getStatusReview() == StatusReviewAndRefund.CONFIRMED)
                                .reduce(0, (integer, review) -> integer + review.getRating(), Integer::sum);
                        productDataCart.setRating(((double)ratingAll / pd.getReviews().size()));
                    } else {
                        productDataCart.setRating(0.0);
                    }

                    PromotionProduct promotionProduct = promotionProductService.getPromotionProductByProductData(pd);
                    Double discountPrice = pd.getCost();
                    Integer discount = 0;
                    if (promotionProduct != null) {
                        if (new Date().getTime() < promotionProduct.getPromotion().getDateEnd().getTime()) {
                            discount = promotionProduct.getDiscount();
                            discountPrice = pd.getCost() * (100 - promotionProduct.getDiscount()) / 100;
                            productDataCart.setDiscount(promotionProduct.getDiscount());
                        }
                    }
                    productDataCart.setDiscount(discount);
                    productDataCart.setDiscountPrice(discountPrice);

                    return productDataCart;
                }
        ).collect(Collectors.toList());
    }

    public Page<ProductDataCart> getProductDataCartPage(Page<ProductData> page) {
        return page.map(pd -> {
            ProductDataCart productDataCart = new ProductDataCart();

            productDataCart.setId(pd.getId());
            productDataCart.setName(pd.getName());
            productDataCart.setDescription(pd.getDescription());
            Image image = imageService.getFirstImageByProductDataId(pd.getId());
            if (image != null) {
                productDataCart.setImage(image.getFilename());
            }
            productDataCart.setPrice(pd.getCost());
            productDataCart.setMaxCount(productService.getProductMaxCount(pd.getId()));

            if (pd.getReviews().size() > 0) {
                Integer ratingAll = pd.getReviews().stream()
                        .filter(review -> review.getStatusReview() == StatusReviewAndRefund.CONFIRMED)
                        .reduce(0, (integer, review) -> integer + review.getRating(), Integer::sum);
                productDataCart.setRating(((double)ratingAll / pd.getReviews().size()));
            } else {
                productDataCart.setRating(0.0);
            }

            Double discountPrice = pd.getCost();
            Integer discount = getDiscount(pd);
            if (discount > 0) {
                discountPrice = pd.getCost() * (100 - discount) / 100;
            }
            productDataCart.setDiscount(discount);
            productDataCart.setDiscountPrice(discountPrice);

            return productDataCart;
        });
    }

    public Integer getDiscount(ProductData pd) {
        PromotionProduct promotionProduct = promotionProductService.getPromotionProductByProductData(pd);
        if (promotionProduct != null) {
            if (new Date().getTime() < promotionProduct.getPromotion().getDateEnd().getTime()) {
                return promotionProduct.getDiscount();
            }
        }
        return 0;
    }

    public Page<ProductDataCart> getProductDataCartsByPromotion(Pageable pageable, Long id) {
        Page<ProductData> productData = getPageProductsByPromotion(id, pageable);
        return getProductDataCartPage(productData);
    }

    public void update(ru.edjll.shop.model.ProductData productData, List<MultipartFile> images) {

        ProductData productDataDomain = productDataRepository.getOne(productData.getId());

        productDataDomain.setName(productData.getName());
        productDataDomain.setDescription(productData.getDescription());
        productDataDomain.setCost(productData.getCost());
        productDataDomain.setShelfLife(productData.getShelfLife());

        Manufacturer manufacturer = manufacturerService.getManufacturerById(productData.getManufacturer());
        Country country = countryService.getCountryById(productData.getCountry());
        ProductCategory category = productCategoryService.getCategoryById(productData.getCategory());

        productDataDomain.setCountry(country);
        productDataDomain.setManufacturer(manufacturer);
        productDataDomain.setCategory(category);

        List<Image> productDataImages = imageService.getImagesByProductDataId(productDataDomain.getId());
        List<Long> imageIds = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> {
                if (!Objects.equals(image.getContentType(), "null")) {
                    imageService.saveProductDataImage(image, productDataDomain);
                } else {
                    try {
                        Long id = Long.parseLong(image.getOriginalFilename());
                        imageIds.add(id);
                    } catch (NullPointerException | NumberFormatException exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }

        productDataImages.forEach(productDataImage -> {
            if (!imageIds.contains(productDataImage.getId())) {
                imageService.deleteProductDataImage(productDataImage.getId());
            }
        });

        List<AttributeValue> attributeValuesWithOneProduct = attributeValueService.getAttributeValuesWithOneProduct(productData.getId());

        Set<AttributeValue> attributeValues = new HashSet<>();
        Set<Attribute> attributes = new HashSet<>();
        Set<Value> values = new HashSet<>();

        productData.getAttributes().forEach(attribute -> {
            AttributeCategory attributeCategoryDomain = attributeCategoryService.save(attribute.getCategory());
            Attribute attributeDomain = attributeService.save(attribute, attributeCategoryDomain);
            Value valueDomain = valueService.save(attribute);
            AttributeValue attributeValueDomain = attributeValueService.save(attributeDomain, valueDomain);

            attributeValues.add(attributeValueDomain);
        });

        productDataDomain.setAttributes(attributeValues);

        productDataRepository.save(productDataDomain);

        deleteUnnecessaryAttributeValues(attributeValues, attributeValuesWithOneProduct);
    }

    public void deleteUnnecessaryAttributeValues(Collection<AttributeValue> source, Collection<AttributeValue> all) {
        List<Long> sourceId = source.stream()
                .map(AttributeValue::getId).collect(Collectors.toList());

        all.stream()
                .filter(item -> sourceId.stream()
                        .noneMatch(av -> av.equals(item.getId())))
                .forEach(item -> {
                    Attribute attribute = item.getAttribute();
                    Value value = item.getValue();

                    attributeValueService.deleteById(item.getId());

                    if (attribute.getAttributeValues().size() == 0 && attribute.getProductCategories().size() == 0) {
                        AttributeCategory attributeCategory = attribute.getCategory();
                        attributeService.deleteById(attribute);
                        if (attributeCategory.getAttributes().size() == 0) {
                            attributeCategoryService.deleteById(attributeCategory.getId());
                        }
                    }
                    if (value.getAttributeValues().size() == 0) {
                        valueService.deleteById(value);
                    }
                });
    }

    public Map<String, String> delete(Long id) {
        Map<String, String> errors = new HashMap<>();
        ProductData productData = productDataRepository.getOne(id);
        if (!productData.getProducts().isEmpty()) {
            errors.put("error1", "Невозможно удалить продукт #" + id + ", т.к. уже были поставки");
        }
        if (!productData.getReviews().isEmpty()) {
            errors.put("error2", "Невозможно удалить продукт #" + id + ", т.к. уже есть отзывы");
        }
        if (!productData.getPromotionProducts().isEmpty()) {
            errors.put("error3", "Невозможно удалить продукт #" + id + ", т.к. он участвует в акции");
        }
        if (errors.isEmpty()) {
            productData.getImages().forEach(image -> imageService.deleteProductDataImage(image.getId()));
            List<AttributeValue> attributeValues = attributeValueService.getAttributeValuesWithOneProduct(productData.getId());
            productData.getBaskets().forEach(basket -> basketService.deleteById(basket.getId()));
            deleteUnnecessaryAttributeValues(new ArrayList<>(), attributeValues);
            productDataRepository.deleteById(id);
        }
        return errors;
    }

    public ru.edjll.shop.model.ProductData getProductDataModelById(Long id) {
        ProductData productData = productDataRepository.getOne(id);

        ru.edjll.shop.model.ProductData productDataModel = new ru.edjll.shop.model.ProductData();

        productDataModel.setId(productData.getId());
        productDataModel.setName(productData.getName());
        productDataModel.setDescription(productData.getDescription());
        productDataModel.setCost(productData.getCost());
        productDataModel.setShelfLife(productData.getShelfLife());
        productDataModel.setManufacturer(productData.getManufacturer().getId());
        productDataModel.setCountry(productData.getCountry().getId());
        productDataModel.setCategory(productData.getCategory().getId());

        List<ru.edjll.shop.model.Attribute> attributes = new ArrayList<>();

        productData.getAttributes().forEach(attributeValue -> {
            ru.edjll.shop.model.Attribute attribute = new ru.edjll.shop.model.Attribute();
            attribute.setId(attributeValue.getId());
            attribute.setName(attributeValue.getAttribute().getName());
            attribute.setDescription(attributeValue.getAttribute().getDescription());
            attribute.setValue(attributeValue.getValue().getValue());
            attribute.setUnit(attributeValue.getValue().getUnit());
            attribute.setCategory(attributeValue.getAttribute().getCategory().getName());
            attributes.add(attribute);
        });

        productDataModel.setAttributes(attributes);

        return productDataModel;
    }

    public Page<ru.edjll.shop.model.rest.ProductData> getPageProductsSearchWithoutPromotions(Pageable pageable, String searchParam, List<Long> id) {
        Page<ProductData> productData;
        if (id == null || id.isEmpty()) {
            if (searchParam.isEmpty()) {
                productData = productDataRepository.findAllWithoutPromotions(new Date(), pageable);
            } else {
                productData = productDataRepository.findAllByNameContainsIgnoreCaseWithoutPromotions(new Date(), searchParam, pageable);
            }
        } else {
            if (searchParam.isEmpty()) {
                productData = productDataRepository.getAllByIdNotInWithoutPromotions(new Date(), id, pageable);
            } else {
                productData = productDataRepository.findAllByNameContainsIgnoreCaseAndIdNotInWithoutPromotions(new Date(), searchParam, id, pageable);
            }
        }

        return productData.map(product -> {
            ru.edjll.shop.model.rest.ProductData productDataRest = new ru.edjll.shop.model.rest.ProductData();
            productDataRest.setId(product.getId());
            productDataRest.setName(product.getName());
            return productDataRest;
        });
    }

    public List<ProductData> getProductsByCategory(ProductCategory productCategory) {
        return productDataRepository.getAllByCategory(productCategory);
    }

    public List<ProductDataCart> getPopularProducts() {
        List<ProductData> productDataList = productDataRepository.findAll();
        List<ProductDataCart> productDataCarts = productDataList.stream().map(productData -> {
            ProductDataCart productDataCart = new ProductDataCart();

            productDataCart.setId(productData.getId());
            productDataCart.setName(productData.getName());
            productDataCart.setDescription(productData.getDescription());
            Image image = imageService.getFirstImageByProductDataId(productData.getId());
            if (image != null) {
                productDataCart.setImage(image.getFilename());
            }
            productDataCart.setPrice(productData.getCost());
            productDataCart.setMaxCount(productService.getProductMaxCount(productData.getId()));

            if (productData.getReviews().size() > 0) {
                Integer ratingAll = productData.getReviews().stream().filter(review -> review.getStatusReview() == StatusReviewAndRefund.CONFIRMED).reduce(0, (integer, review) -> integer + review.getRating(), Integer::sum);
                productDataCart.setRating(((double)ratingAll / productData.getReviews().size()));
            } else {
                productDataCart.setRating(0.0);
            }

            PromotionProduct promotionProduct = promotionProductService.getPromotionProductByProductData(productData);
            Double discountPrice = productData.getCost();
            Integer discount = 0;
            if (promotionProduct != null) {
                if (new Date().getTime() < promotionProduct.getPromotion().getDateEnd().getTime()) {
                    discount = promotionProduct.getDiscount();
                    discountPrice = productData.getCost() * (100 - promotionProduct.getDiscount()) / 100;
                    productDataCart.setDiscount(promotionProduct.getDiscount());
                }
            }
            productDataCart.setDiscount(discount);
            productDataCart.setDiscountPrice(discountPrice);

            return productDataCart;
        }).collect(Collectors.toList());

        productDataCarts.sort((p1, p2) -> (int)Math.round(p2.getRating() - p1.getRating()));

        return productDataCarts.stream().limit(5).collect(Collectors.toList());
    }
}