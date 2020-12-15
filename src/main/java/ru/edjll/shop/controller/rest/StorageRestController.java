package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.model.Storage;
import ru.edjll.shop.service.StorageService;

import java.util.Collections;
import java.util.Map;

@RestController
public class StorageRestController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/storage/create")
    public ResponseEntity createStorage(
            @RequestPart(name = "storage", required = false) @Validated Storage storage,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        storageService.save(storage);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/storage/update")
    public ResponseEntity updateStorage(
            @RequestPart(name = "storage", required = false) @Validated Storage storage,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        storageService.update(storage);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/storage/delete")
    public ResponseEntity<Map<String, String>> deleteStorage(@RequestPart(name = "storage") IdWrapper idWrapper) {
        try {
            storageService.deleteStorage(idWrapper.getId());
            return ResponseEntity.ok().body(null);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Удаление невозможно, есть связанные сущности"));
        }
    }
}
