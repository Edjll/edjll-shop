package ru.edjll.shop.validation.attributeCategory.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.AttributeCategory;
import ru.edjll.shop.service.AttributeCategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, AttributeCategory> {

    @Autowired
    private AttributeCategoryService attributeCategoryService;


    @Override
    public boolean isValid(AttributeCategory attributeCategory, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.AttributeCategory attributeCategoryDomain = attributeCategoryService.getAttributeCategoryByName(attributeCategory.getName());
        if (attributeCategoryDomain == null || attributeCategoryDomain.getId().equals(attributeCategory.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.attributeCategory.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
