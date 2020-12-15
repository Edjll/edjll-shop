package ru.edjll.shop.validation.country.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.service.CountryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Country> {

    @Autowired
    private CountryService countryService;


    @Override
    public boolean isValid(Country country, ConstraintValidatorContext ctx) {
        Country countryDomain = countryService.getCountryByName(country.getName());
        if (countryDomain == null || countryDomain.getId().equals(country.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.country.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
