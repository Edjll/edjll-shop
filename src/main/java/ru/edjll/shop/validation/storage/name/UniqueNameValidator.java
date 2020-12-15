package ru.edjll.shop.validation.storage.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.shop.model.Storage;
import ru.edjll.shop.service.StorageService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Storage> {

    @Autowired
    private StorageService storageService;


    @Override
    public boolean isValid(Storage storage, ConstraintValidatorContext ctx) {
        ru.edjll.shop.domain.Storage storageDomain = storageService.getStorageByName(storage.getName());
        if (storageDomain == null || storageDomain.getId().equals(storage.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.storage.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}
