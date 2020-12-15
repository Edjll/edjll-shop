package ru.edjll.shop.validation.productData.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.ProductData;
import ru.edjll.shop.service.ProductDataService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, ProductData> {

    @Autowired
    private ProductDataService productDataService;


    @Override
    public boolean isValid(ProductData productData, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.ProductData productDataDomain = productDataService.getProductDataByName(productData.getName());
        if (productDataDomain == null || productDataDomain.getId().equals(productData.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.productData.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
