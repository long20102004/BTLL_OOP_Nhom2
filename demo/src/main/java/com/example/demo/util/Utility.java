package com.example.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Component
public class Utility {
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    public String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return input.trim();
    }
    public String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().substring(Math.max(file.getOriginalFilename().length() - 5, 0));        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return "/uploads/" + fileName;
    }
}