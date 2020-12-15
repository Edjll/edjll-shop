package ru.edjll.shop.validation.productData;

import org.springframework.stereotype.Component;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.model.ProductData;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductDataValidator {

    public Map<String, String> validate(ProductData productData) {

        Map<String, String> errors = new HashMap<>();

        for (int i = 0; i < productData.getAttributes().size(); i++) {
            Attribute attribute = productData.getAttributes().get(i);
            if (productData.getAttributes().stream()
                    .anyMatch(a -> a.getName().equals(attribute.getName()) && !a.equals(attribute))) {

                errors.put("product.attributes.wrapper[" + i + "].name", "Аттрибут с таким название уже существует");
            }
        }

        return errors;
    }
}
