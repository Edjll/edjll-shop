package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Product;
import ru.edjll.shop.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product getFirstProductByProductId(Long id) {
        return productRepository.sellableProducts(id).stream().findFirst().orElse(null);
    }

    public Long getProductMaxCount(Long id) {
        return productRepository.getMaxCountProduct(id);
    }
}
