package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.City;
import ru.edjll.shop.domain.Storage;
import ru.edjll.shop.service.CityService;
import ru.edjll.shop.service.StorageService;

import java.util.List;
import java.util.Map;

@Controller
public class StorageController {
    @Autowired
    private CityService cityService;

    @Autowired
    private StorageService storageService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/storage/create")
    public String getCreateStoragePage(Map<String, Object> model) {
        List<City> cities = cityService.getAllCities();
        model.put("cities", cities);
        return "admin/storage/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/storage/update/{id}")
    public String getUpdateStoragePage(@PathVariable Long id,
                                       Map<String, Object> model
    ) {
        Storage storage = storageService.getStorage(id);
        List<City> cities = cityService.getAllCities();

        model.put("storage", storage);
        model.put("cities", cities);

        return "admin/storage/update";
    }
}
