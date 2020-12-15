package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.domain.Image;
import ru.edjll.shop.domain.Manufacturer;
import ru.edjll.shop.domain.ProductCategory;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.model.ProductData;
import ru.edjll.shop.service.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ProductDataController {

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/product/create")
    public String getProductsCreate(Map<String, Object> model) {

        List<Country> countries = countryService.getAllCountries();
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();

        model.put("countries", countries);
        model.put("manufacturers", manufacturers);

        return "admin/product/create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/product/update/{id}")
    public String getProductsUpdate(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        ProductData productDataModel = productDataService.getProductDataModelById(id);
        List<Country> countries = countryService.getAllCountries();
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        Country country = countryService.getCountryById(productDataModel.getCountry());
        Manufacturer manufacturer = manufacturerService.getManufacturerById(productDataModel.getManufacturer());
        ProductCategory productCategory = productCategoryService.getCategoryById(productDataModel.getCategory());
        List<Image> images = imageService.getImagesByProductDataId(productDataModel.getId());
        Set<Attribute> attributes = attributeService.getAttributesByProductCategory(productCategory.getId());

        model.put("productData", productDataModel);
        model.put("countries", countries);
        model.put("country", country);
        model.put("manufacturers", manufacturers);
        model.put("manufacturer", manufacturer);
        model.put("productCategory", productCategory);
        model.put("images", images);
        model.put("attributes", attributes);

        return "admin/product/update";
    }
}
