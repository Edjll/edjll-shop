package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.repository.ImageRepository;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    @Value("${manufacturer.upload.path}")
    private String manufacturerUploadPath;

    @Value("${promotion.upload.path}")
    private String promotionUploadPath;

    @Value("${productData.upload.path}")
    private String productDataUploadPath;

    @Value("${review.upload.path}")
    private String reviewUploadPath;

    private String saveProductDataImageInFileSystem(MultipartFile file) {
        return fileService.createFile(productDataUploadPath, file);
    }

    private void deleteProductDataImageInFileSystem(String filename) {
        fileService.deleteFile(productDataUploadPath, filename);
    }

    private String savePromotionImageInFileSystem(MultipartFile file) {
        return fileService.createFile(promotionUploadPath, file);
    }

    private void deletePromotionImageInFileSystem(String filename) {
        fileService.deleteFile(promotionUploadPath, filename);
    }

    private String saveManufacturerImageInFileSystem(MultipartFile file) {
        return fileService.createFile(manufacturerUploadPath, file);
    }

    private void deleteManufacturerImageInFileSystem(String filename) {
        fileService.deleteFile(manufacturerUploadPath, filename);
    }

    private String saveReviewImageInFileSystem(MultipartFile file) {
        return fileService.createFile(reviewUploadPath, file);
    }

    private void deleteReviewImageInFileSystem(String filename) {
        fileService.deleteFile(reviewUploadPath, filename);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image saveProductDataImage(MultipartFile file, ProductData productData) {
        Image image = new Image();
        String filename = saveProductDataImageInFileSystem(file);
        image.setFilename(filename);
        image.setProductData(productData);
        return save(image);
    }

    public Image savePromotionImage(MultipartFile file, Promotion promotion) {
        Image image = new Image();
        String filename = savePromotionImageInFileSystem(file);
        image.setFilename(filename);
        image.setPromotion(promotion);
        return save(image);
    }

    public Image saveManufacturerImage(MultipartFile file, Manufacturer manufacturer) {
        Image image = new Image();
        String filename = saveManufacturerImageInFileSystem(file);
        image.setFilename(filename);
        image.setManufacturer(manufacturer);
        return save(image);
    }

    public void delete(Image image) {
        image.setManufacturer(null);
        image.setPromotion(null);
        image.setProductData(null);
        imageRepository.deleteById(image.getId());
    }

    public void delete(Long id) {
        Image image = getImageById(id);
        delete(image);
    }

    public void deleteProductDataImage(Long id) {
        Image image = getImageById(id);
        delete(id);
        deleteProductDataImageInFileSystem(image.getFilename());
    }

    public void deletePromotionImage(Long id) {
        Image image = getImageById(id);
        delete(id);
        deletePromotionImageInFileSystem(image.getFilename());
    }

    public void deleteManufacturerImage(Long id) {
        Image image = getImageById(id);
        delete(id);
        deleteManufacturerImageInFileSystem(image.getFilename());
    }

    public List<Image> getImagesByProductDataId(Long id) {
        return imageRepository.getImagesByProductDataId(id);
    }

    public List<Image> getImagesByPromotionId(Long id) {
        return imageRepository.getImagesByPromotionId(id);
    }

    public List<Image> getImagesByManufacturerId(Long id) {
        return imageRepository.getImagesByManufacturerId(id);
    }

    public String getImageByPromotionId(Long id) {
        return imageRepository.getImageByPromotionId(id).getFilename();
    }

    public String getImageByManufacturerId(Long id) {
        return imageRepository.getImageByManufacturerId(id).getFilename();
    }

    public Image getImageById(Long id) {
        return imageRepository.getOne(id);
    }

    public Image getFirstImageByProductDataId(Long id) {
        return imageRepository.getFirstByProductDataId(id);
    }
}
