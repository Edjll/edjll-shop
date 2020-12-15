package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.SaleProduct;
import ru.edjll.shop.repository.SaleProductRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleProductService {
    @Autowired
    private SaleProductRepository saleProductRepository;

    public List<Object> getProductSalesByDates(LocalDate dateStart, LocalDate dateEnd) {
        return saleProductRepository.getProductSalesByDates(dateStart, dateEnd);
    }

    public List<Object> getProductStatistics(Long id, LocalDate dateStart, LocalDate dateEnd) {
        return  saleProductRepository.getProductStatistics(id, dateStart, dateEnd);
    }

    public SaleProduct save(SaleProduct saleProduct) {
        return saleProductRepository.save(saleProduct);
    }

    public List<SaleProduct> getSaleProductsBySale(Long sale) {
        return saleProductRepository.getAllBySaleId(sale);
    }

    public SaleProduct getSaleProductById(Long id) {
        return saleProductRepository.getOne(id);
    }
}
