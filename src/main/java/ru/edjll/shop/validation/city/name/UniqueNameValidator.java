package ru.edjll.shop.validation.city.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.City;
import ru.edjll.shop.service.CityService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, City> {

    @Autowired
    private CityService cityService;


    @Override
    public boolean isValid(City city, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.City cityDomain = cityService.getCityByName(city.getName());
        if (cityDomain == null || cityDomain.getId().equals(city.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.city.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
