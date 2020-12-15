package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.domain.Image;
import ru.edjll.shop.domain.Manufacturer;
import ru.edjll.shop.repository.CountryRepository;
import ru.edjll.shop.repository.ManufacturerRepository;

import java.util.*;

@Service
public class ManufacturerService {

    @Autowired
    private FileService fileService;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ImageService imageService;

    @Value("${manufacturer.upload.path}")
    private String manufacturerUploadPath;

    public Manufacturer getManufacturerById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    public void saveManufacturer(ru.edjll.shop.model.Manufacturer manufacturer, List<MultipartFile> images) {
        Manufacturer manufacturerDomain = new Manufacturer();

        manufacturerDomain.setName(manufacturer.getName());
        manufacturerDomain.setDescription(manufacturer.getDescription());
        manufacturerDomain.setCountry(countryRepository.getOne(manufacturer.getCountry()));

        Manufacturer manufacturerDomainSaved = manufacturerRepository.save(manufacturerDomain);

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> imageService.saveManufacturerImage(image, manufacturerDomainSaved));
        }
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Map<String, String> deleteManufacturerById(Long id) {
        Map<String, String> errors = new HashMap();
        Manufacturer manufacturer = getManufacturerById(id);
        if (manufacturer.getProductData().isEmpty()) {
            imageService.deleteManufacturerImage(manufacturer.getLogo().getId());
            manufacturerRepository.deleteById(id);
        } else {
            errors.put("error", "Удаление невозможно, у производителя #" + id + " есть продукты");
        }
        return errors;
    }

    public Page<Manufacturer> getPageManufacturers(Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    public Manufacturer getManufacturerByName(String name) {
        return  manufacturerRepository.getManufacturerByName(name);
    }

    public void updateManufacturer(ru.edjll.shop.model.Manufacturer manufacturer, List<MultipartFile> images) {
        Manufacturer manufacturerDomain = manufacturerRepository.getOne(manufacturer.getId());

        manufacturerDomain.setName(manufacturer.getName());
        manufacturerDomain.setDescription(manufacturer.getDescription());
        manufacturerDomain.setCountry(countryRepository.getOne(manufacturer.getCountry()));

        Manufacturer manufacturerDomainSaved = manufacturerRepository.save(manufacturerDomain);

        List<Image> manufacturerImages = imageService.getImagesByManufacturerId(manufacturerDomain.getId());
        List<Long> imageIds = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            images.forEach(image -> {
                if (!Objects.equals(image.getContentType(), "null")) {
                    imageService.saveManufacturerImage(image, manufacturerDomain);
                } else {
                    try {
                        Long id = Long.parseLong(image.getOriginalFilename());
                        imageIds.add(id);
                    } catch (NullPointerException | NumberFormatException exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }

        manufacturerImages.forEach(manufacturerImage -> {
            if (!imageIds.contains(manufacturerImage.getId())) {
                imageService.deleteManufacturerImage(manufacturerImage.getId());
            }
        });

        manufacturerRepository.save(manufacturerDomain);
    }

    public Page<ru.edjll.shop.model.Manufacturer> getPageManufacturersModel(Pageable pageable) {
        return manufacturerRepository.findAllModel(pageable);
    }
}
