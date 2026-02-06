package com.project.foodapp.cloudinary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class CloudinaryUploadController {

    private final ImageStorageService imageStorageService;

    @PostMapping
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = imageStorageService.uploadFile(file);

        return ResponseEntity.ok("SAVED SUCCESSFULLY: " + imageUrl);
    }
}
