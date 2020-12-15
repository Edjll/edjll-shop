package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class ControllerUtils {

    @Autowired
    private MessageSource messageSource;

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError ->  fieldError.getObjectName() + "."
                              + fieldError.getField(),
                fieldError -> messageSource.getMessage(fieldError.getDefaultMessage(), null, Locale.getDefault())
        );
        return bindingResult.getFieldErrors()
                .stream()
                .collect(collector);
    }
}
