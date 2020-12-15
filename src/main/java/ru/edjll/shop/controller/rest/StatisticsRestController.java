package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.model.StatisticsDates;
import ru.edjll.shop.service.*;

import java.util.List;

@RestController
public class StatisticsRestController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SaleProductService saleProductService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RefundService refundService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/statistics/sales")
    public ResponseEntity<List<Object>> getSalesByDates(
            @RequestPart(name = "statistics") StatisticsDates statisticsDates
    ) {
        return ResponseEntity.ok().body(saleService.getStatistics(statisticsDates));
    }
}
