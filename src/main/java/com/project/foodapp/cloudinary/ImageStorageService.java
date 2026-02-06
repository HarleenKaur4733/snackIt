package com.project.foodapp.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String uploadFile(MultipartFile file);

    void deleteFile(String publicId);
}
