package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.ProductData;
import ru.edjll.shop.domain.PromotionProduct;
import ru.edjll.shop.repository.PromotionProductRepository;

@Service
public class PromotionProductService {

    @Autowired
    private PromotionProductRepository promotionProductRepository;

    public PromotionProduct save(PromotionProduct promotionProduct) {
        return promotionProductRepository.save(promotionProduct);
    }

    public void delete(PromotionProduct promotionProduct) {
        promotionProductRepository.delete(promotionProduct);
    }

    public PromotionProduct getPromotionProductByProductData(ProductData productData) {
        return promotionProductRepository.getAllByProductDataId(productData.getId());
    }
}
