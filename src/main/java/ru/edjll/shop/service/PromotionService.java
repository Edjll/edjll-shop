package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.domain.Image;
import ru.edjll.shop.domain.Product;
import ru.edjll.shop.domain.Promotion;
import ru.edjll.shop.domain.PromotionProduct;
import ru.edjll.shop.model.DiscountProduct;
import ru.edjll.shop.repository.PromotionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private PromotionProductService promotionProductService;

    @Autowired
    private ImageService imageService;

    @Value("${promotion.upload.path}")
    private String promotionUploadPath;

    public List<Promotion> getAllPromotion() {
        return promotionRepository.findAll();
    }

    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.getOne(id);

        promotion.getPromotionProducts().forEach(promotionProduct -> {
            promotionProductService.delete(promotionProduct);
        });

        List<Image> promotionImages = imageService.getImagesByPromotionId(promotion.getId());
        promotionImages.forEach(promotionImage -> {
            imageService.deletePromotionImage(promotionImage.getId());
        });

        promotionRepository.deleteById(id);
    }

    private String savePromotionBanner(MultipartFile file) {
        return fileService.createFile(promotionUploadPath, file);
    }

    public void savePromotion(ru.edjll.shop.model.Promotion promotion, List<MultipartFile> images) {
        Promotion promotionDomain = new Promotion();

        promotionDomain.setName(promotion.getName());
        promotionDomain.setDescription(promotion.getDescription());
        promotionDomain.setDateStart(promotion.getDateStart());
        promotionDomain.setDateEnd(promotion.getDateEnd());

        Promotion promotionDomainSaved = promotionRepository.save(promotionDomain);

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> imageService.savePromotionImage(image, promotionDomain));
        }

        List<PromotionProduct> promotionProducts = promotion.getProducts().stream().map(discountProduct -> {
            PromotionProduct promotionProduct = new PromotionProduct();
            promotionProduct.setDiscount(discountProduct.getDiscount());
            promotionProduct.setPromotion(promotionDomainSaved);
            promotionProduct.setProductData(productDataService.getProductDataById(discountProduct.getProduct()));
            return promotionProductService.save(promotionProduct);
        }).collect(Collectors.toList());

        promotionDomain.setPromotionProducts(promotionProducts);

        promotionRepository.save(promotionDomain);
    }

    public Page<Promotion> getPagePromotion(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    public Promotion getPromotionByName(String name) {
        return promotionRepository.getPromotionByName(name);
    }

    public void updatePromotion(ru.edjll.shop.model.Promotion promotion, List<MultipartFile> images) {
        Promotion promotionDomain = promotionRepository.getOne(promotion.getId());

        promotionDomain.setName(promotion.getName());
        promotionDomain.setDescription(promotion.getDescription());
        promotionDomain.setDateStart(promotion.getDateStart());
        promotionDomain.setDateEnd(promotion.getDateEnd());

        Promotion promotionDomainSaved = promotionRepository.save(promotionDomain);

        List<Image> promotionImages = imageService.getImagesByPromotionId(promotionDomain.getId());
        List<Long> imageIds = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> {
                if (!Objects.equals(image.getContentType(), "null")) {
                    imageService.savePromotionImage(image, promotionDomain);
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

        promotionImages.forEach(promotionImage -> {
            if (!imageIds.contains(promotionImage.getId())) {
                imageService.deletePromotionImage(promotionImage.getId());
            }
        });

        promotionDomain.getPromotionProducts().forEach(promotionProduct -> {
            promotionProductService.delete(promotionProduct);
        });

        List<PromotionProduct> promotionProducts = promotion.getProducts().stream().map(discountProduct -> {
            PromotionProduct promotionProduct = new PromotionProduct();
            promotionProduct.setDiscount(discountProduct.getDiscount());
            promotionProduct.setPromotion(promotionDomainSaved);
            promotionProduct.setProductData(productDataService.getProductDataById(discountProduct.getProduct()));
            return promotionProductService.save(promotionProduct);
        }).collect(Collectors.toList());

        promotionDomain.setPromotionProducts(promotionProducts);

        promotionRepository.save(promotionDomain);
    }

    public ru.edjll.shop.model.Promotion getPromotionModelById(Long id) {
        Promotion promotionDomain = promotionRepository.getOne(id);
        ru.edjll.shop.model.Promotion promotionModel = new ru.edjll.shop.model.Promotion();

        promotionModel.setId(promotionDomain.getId());
        promotionModel.setName(promotionDomain.getName());
        promotionModel.setDescription(promotionDomain.getDescription());
        promotionModel.setDateStart(promotionDomain.getDateStart());
        promotionModel.setDateEnd(promotionDomain.getDateEnd());

        List<DiscountProduct> discountProducts = promotionDomain.getPromotionProducts().stream().map(promotionProduct -> {
            DiscountProduct discountProduct = new DiscountProduct();
            discountProduct.setProduct(promotionProduct.getProductData().getId());
            discountProduct.setName(promotionProduct.getProductData().getName());
            discountProduct.setDiscount(promotionProduct.getDiscount());
            return  discountProduct;
        }).collect(Collectors.toList());

        promotionModel.setProducts(discountProducts);

        return promotionModel;
    }

    public Promotion findById(Long id) {
        return promotionRepository.getOne(id);
    }

    public Promotion findActivePromotion() {
        List<Promotion> promotions = promotionRepository.findAll();
        for (Promotion promotion : promotions) {
            if (promotion.getDateEnd().getTime() > new Date().getTime()) {
                return promotion;
            }
        }
        return null;
    }

    public Promotion getPromotionById(Long id) {
        return promotionRepository.getOne(id);
    }
}
