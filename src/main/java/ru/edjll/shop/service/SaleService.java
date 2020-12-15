package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.StatisticsDates;
import ru.edjll.shop.model.cart.Payment;
import ru.edjll.shop.model.cart.SaleDelivery;
import ru.edjll.shop.repository.SaleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleProductService saleProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private DeliveryService deliveryService;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSale(Long id) {
        return saleRepository.getOne(id);
    }

    public Page<Sale> getPageSales(Pageable pageable) {
        return  saleRepository.findAll(pageable);
    }

    public List<ru.edjll.shop.model.Sale> getSalesDates(LocalDate dateStart, LocalDate dateEnd) {
        return saleRepository.getSaleDate(LocalDateTime.of(dateStart, LocalTime.MAX), LocalDateTime.of(dateEnd, LocalTime.MAX));
    }

    public List<ru.edjll.shop.model.Sale> test(LocalDate dateStart, LocalDate dateEnd) {
        return saleRepository.getSaleDate(LocalDateTime.of(dateStart, LocalTime.MAX), LocalDateTime.of(dateEnd, LocalTime.MAX));
    }

    public Page<Sale> getSalesPageByUser(Long id, Pageable pageable) {
        return saleRepository.getAllByUserId(id, pageable);
    }

    public Sale sale(User authUser, Payment payment) {
        User user = userService.getUserById(authUser.getId());

        Set<Basket> baskets = user.getBaskets();

        Sale sale = new Sale();

        sale.setUser(user);
        sale.setDate(LocalDateTime.now());

        Sale saleSaved = saleRepository.save(sale);

        baskets.stream().filter(b -> {
            Long maxCount = productService.getProductMaxCount(b.getProductData().getId());
            return maxCount != null && maxCount > 0;
        }).forEach(basket -> {

            for (int i = 0; i < basket.getCount(); i++) {
                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setSale(saleSaved);
                saleProduct.setProduct(productService.getFirstProductByProductId(basket.getProductData().getId()));
                saleProduct.setCost(basket.getProductData().getCost());
                SaleProduct saleProductSaved = saleProductService.save(saleProduct);

                Delivery delivery = new Delivery();
                delivery.setAddress(payment.getAddress());
                delivery.setSaleProduct(saleProductSaved);
                delivery.setStorage(saleProductSaved.getProduct().getSupply().getStorage());
                delivery.setStatusDelivery(StatusDelivery.PROCESSING);
                deliveryService.save(delivery);
            }

            basketService.deleteById(basket.getId());
        });

        return saleSaved;
    }

    public Page<ru.edjll.shop.model.cart.Sale> getSaleModel(Pageable pageable) {
        Page<Sale> sales = saleRepository.findAll(pageable);

        return sales.map(sale -> {
            ru.edjll.shop.model.cart.Sale saleModel = new ru.edjll.shop.model.cart.Sale();
            saleModel.setId(sale.getId());
            saleModel.setUser(sale.getUser().getEmail());
            saleModel.setDate(sale.getDate());
            SaleProduct saleProduct = sale.getSaleProducts().stream().findFirst().orElse(null);
            saleModel.setAddress(saleProduct.getDelivery().getAddress());
            return saleModel;
        });
    }

    public void saleUpdate(SaleDelivery saleDelivery) {
        saleDelivery.getDeliveries().forEach(delivery -> {
            Delivery deliveryDomain = deliveryService.getDeliveryById(delivery.getId());
            for (StatusDelivery statusDelivery : StatusDelivery.values()) {
                if (statusDelivery.ordinal() == delivery.getStatus()) {
                    deliveryDomain.setStatusDelivery(statusDelivery);
                }
            }
            deliveryService.save(deliveryDomain);
        });
    }

    public Sale getSaleBySaleProductId(SaleProduct saleProduct) {
        return saleRepository.getBySaleProducts(saleProduct);
    }

    public List<Object> getStatistics(StatisticsDates statisticsDates) {
        return saleRepository.getStatistics(LocalDateTime.of(statisticsDates.getDateStart(), LocalTime.MIN), LocalDateTime.of(statisticsDates.getDateEnd(), LocalTime.MAX));
    }
}
