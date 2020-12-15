package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.StatusReviewAndRefund;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.Refund;
import ru.edjll.shop.repository.RefundRepository;

import java.util.Date;
import java.util.List;

@Service
public class RefundService {

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private SaleProductService saleProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private DeliveryService deliveryService;

    public List<Object> getProductsRefund() {
        return refundRepository.getProductsRefund();
    }

    public ru.edjll.shop.domain.Refund getRefundById(Long id) {
        return refundRepository.getOne(id);
    }

    public Page<ru.edjll.shop.domain.Refund> getRefundPage(Pageable pageable) {
        return refundRepository.findAll(pageable);
    }

    public void addRefund(Refund refund) {
        ru.edjll.shop.domain.Refund refundDomain = new ru.edjll.shop.domain.Refund();

        refundDomain.setReason(refund.getReason());
        refundDomain.setDate(new Date());
        refundDomain.setSaleProduct(saleProductService.getSaleProductById(refund.getSaleProduct()));
        refundDomain.setStatusRefund(StatusReviewAndRefund.PROCESSING);

        for(TypeRefund typeRefund : TypeRefund.values()) {
            if (refund.getType() == typeRefund.ordinal()) {
                refundDomain.setTypeRefund(typeRefund);
            }
        }

        refundRepository.save(refundDomain);
    }

    public void updateRefund(Refund refund) {
        ru.edjll.shop.domain.Refund refundDomain = refundRepository.getOne(refund.getId());

        refundDomain.setComment(refund.getComment());
        for (StatusReviewAndRefund status : StatusReviewAndRefund.values()) {
            if (refund.getStatus() == status.ordinal()) {
                refundDomain.setStatusRefund(status);
            }
        }

        if (refundDomain.getTypeRefund() == TypeRefund.PRODUCT) {
            if (refund.getStatus() == 1) {
                Product product = productService.getFirstProductByProductId(refundDomain.getSaleProduct().getProduct().getProductData().getId());
                Sale sale = saleService.getSaleBySaleProductId(refundDomain.getSaleProduct());

                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setCost(0.0);
                saleProduct.setProduct(product);
                saleProduct.setSale(sale);
                SaleProduct saleProductSaved = saleProductService.save(saleProduct);

                Delivery delivery = new Delivery();
                delivery.setAddress(sale.getSaleProducts().stream().findFirst().orElse(null).getDelivery().getAddress());
                delivery.setSaleProduct(saleProductSaved);
                delivery.setStorage(product.getSupply().getStorage());
                delivery.setStatusDelivery(StatusDelivery.PROCESSING);
                deliveryService.save(delivery);

                refundDomain.setRefundSaleProduct(saleProduct);

            }
        }

        refundRepository.save(refundDomain);
    }

    public Page<ru.edjll.shop.domain.Refund> getRefundsPageByUser(User user, Pageable pageable) {
        return refundRepository.getAllByUser(user.getId(), pageable);
    }

    public void updateUserRefund(Refund refund) {
        ru.edjll.shop.domain.Refund refundDomain = refundRepository.getOne(refund.getId());

        refundDomain.setReason(refund.getReason());

        for(TypeRefund typeRefund : TypeRefund.values()) {
            if (refund.getType() == typeRefund.ordinal()) {
                refundDomain.setTypeRefund(typeRefund);
            }
        }

        refundDomain.setStatusRefund(StatusReviewAndRefund.PROCESSING);

        refundRepository.save(refundDomain);
    }
}
