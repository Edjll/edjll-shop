package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Product;
import ru.edjll.shop.domain.Supply;
import ru.edjll.shop.model.SupplyProductData;
import ru.edjll.shop.repository.SupplyRepository;

import java.util.List;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductService productService;

    public Page<Supply> getPageSupply(Pageable pageable) {
        return supplyRepository.findAll(pageable);
    }

    public Supply getSupply(Long id) {
        return supplyRepository.getOne(id);
    }

    public void saveSupply(ru.edjll.shop.model.Supply supply) {
        Supply supplyDomain = new Supply();

        supplyDomain.setDate(supply.getDate());
        supplyDomain.setStorage(storageService.getStorage(supply.getStorage()));

        Supply supplyDomainSaved = supplyRepository.save(supplyDomain);

        supply.getProducts().forEach(supplyProductData -> {
            for (int i = 0; i < supplyProductData.getCount(); i++) {
                Product product = new Product();
                product.setProductData(productDataService.getProductDataById(supplyProductData.getProduct()));
                product.setProductionDate(supply.getDate());
                product.setRejection(false);
                product.setSupply(supplyDomainSaved);
                productService.save(product);
            }
        });

        supplyRepository.save(supplyDomain);
    }

    public void updateSupply(ru.edjll.shop.model.Supply supply) {
        Supply supplyDomain = supplyRepository.getOne(supply.getId());

        supplyDomain.setDate(supply.getDate());
        supplyDomain.setStorage(storageService.getStorage(supply.getStorage()));

        supplyRepository.save(supplyDomain);
    }

    public List<SupplyProductData> getAllSupplyProductData(Long id) {
        return supplyRepository.getAllSupplyProductData(id);
    }
}
