package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.model.IdWrapper;
import ru.edjll.shop.model.Manufacturer;
import ru.edjll.shop.service.ManufacturerService;

import java.util.List;
import java.util.Map;

@RestController
public class ManufacturerRestController {
    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/manufacturer/get/all")
    public Page<Manufacturer> getManufacturer(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "15") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return manufacturerService.getPageManufacturersModel(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/manufacturer/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createManufacturer(
            @RequestPart(name = "manufacturer", required = false) @Validated Manufacturer manufacturer,
            @RequestPart(name = "manufacturerLogo", required = false) List<MultipartFile> manufacturerLogo,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        manufacturerService.saveManufacturer(manufacturer, manufacturerLogo);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/manufacturer/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateManufacturer(
            @RequestPart(name = "manufacturer", required = false) @Validated Manufacturer manufacturer,
            @RequestPart(name = "manufacturerLogo", required = false) List<MultipartFile> manufacturerLogo,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        manufacturerService.updateManufacturer(manufacturer, manufacturerLogo);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/manufacturer/delete")
    public ResponseEntity<Map<String, String>> deleteManufacturer(@RequestPart(name = "manufacturer") IdWrapper idWrapper) {
        Map<String, String> errors = manufacturerService.deleteManufacturerById(idWrapper.getId());
        if (errors.isEmpty()) return ResponseEntity.ok().body(null);
        return ResponseEntity.badRequest().body(errors);
    }
}
