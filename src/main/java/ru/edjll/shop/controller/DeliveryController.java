package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ru.edjll.shop.service.DeliveryService;

@Controller
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
}
