package com.demo.service;

import com.demo.model.ImageData;
import com.demo.repository.StorageRepository;
import com.demo.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (imageData != null)
            return "file uploaded: " + file.getOriginalFilename();
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> imageDataOptional = storageRepository.findByName(fileName);
            byte[] image = ImageUtils.decompressImage(imageDataOptional.get().getImageData());
            return image;
    }
}
