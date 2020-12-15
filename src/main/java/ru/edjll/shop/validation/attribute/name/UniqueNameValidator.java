package ru.edjll.shop.validation.attribute.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.service.AttributeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Attribute> {

    @Autowired
    private AttributeService attributeService;


    @Override
    public boolean isValid(Attribute attribute, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.Attribute attributeDomain = attributeService.getAttributeByName(attribute.getName());
        if (attributeDomain == null || attributeDomain.getId().equals(attribute.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.attribute.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
