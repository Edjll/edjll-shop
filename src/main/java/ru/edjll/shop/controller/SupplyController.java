package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Storage;
import ru.edjll.shop.domain.Supply;
import ru.edjll.shop.model.SupplyProductData;
import ru.edjll.shop.service.StorageService;
import ru.edjll.shop.service.SupplyService;

import java.util.List;
import java.util.Map;

@Controller
public class SupplyController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private SupplyService supplyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/supply/create")
    public String getCreateSupplyPage(Map<String, Object> model) {
        List<Storage> storages = storageService.getAllStorages();
        model.put("storages", storages);
        return "admin/supply/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/supply/update/{id}")
    public String getUpdateSupplyPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        Supply supply = supplyService.getSupply(id);
        List<Storage> storages = storageService.getAllStorages();
        List<SupplyProductData> supplyProductData = supplyService.getAllSupplyProductData(supply.getId());

        model.put("supply", supply);
        model.put("supplyProductData", supplyProductData);
        model.put("storages", storages);

        return "admin/supply/update";
    }
}
