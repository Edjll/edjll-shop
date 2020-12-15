package ru.edjll.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {

    private void checkDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdir();
    }

    public String createFile(String path, MultipartFile file) {
        try {
            if (file.isEmpty()) return null;
            checkDir(path);
            String filename = generateFilename(file.getContentType());
            file.transferTo(new File(path + "/" + filename));
            return filename;
        } catch (IOException exception) {
            return null;
        }
    }

    private String getExtensionFile(String contentType) {
        Pattern pattern = Pattern.compile("(?<=\\/).*");
        Matcher matcher = pattern.matcher(contentType);
        matcher.find();
        return matcher.group();
    }

    private String generateFilename(String contentType) {
        return UUID.randomUUID().toString() + "." + getExtensionFile(contentType);
    }

    public void deleteFile(String path, String filename) {
        File file = new File(path + "/" + filename);
        file.delete();
    }
}
