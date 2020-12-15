package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Delivery;
import ru.edjll.shop.domain.StatusDelivery;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Page<String> getUserAddresses(Pageable pageable, String searchParam, User user) {
        Page<String> deliveries;
        if (searchParam.length() > 0) {
            deliveries = deliveryRepository.getAllByUserWithoutCase(searchParam, user.getId(), pageable);
        }
        else {
            deliveries = deliveryRepository.getAllByUser(user.getId(), pageable);
        }
        return deliveries;
    }

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public void sentDelivery(Long id) {
        Delivery delivery = deliveryRepository.getOne(id);
        delivery.setStatusDelivery(StatusDelivery.SENT);
        deliveryRepository.save(delivery);
    }

    public void delivered(Long id) {
        Delivery delivery = deliveryRepository.getOne(id);
        delivery.setStatusDelivery(StatusDelivery.DELIVERED);
        deliveryRepository.save(delivery);
    }

    public Delivery getDeliveryById(Long id) {
        return  deliveryRepository.getOne(id);
    }
}
