package com.example.mediaService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.mediaService.Dto.MediaUploadResponse;
import com.example.mediaService.service.S3Service;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<MediaUploadResponse> uploadMedia(
            @RequestParam("vehicleId") Long vehicleId,
            @RequestPart("file") MultipartFile file) {

        try {
            String s3Url = s3Service.uploadFile(file);

            MediaUploadResponse response = new MediaUploadResponse();
            response.setS3Url(s3Url);
            response.setFileName(file.getOriginalFilename());
            response.setFileType(file.getContentType());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // log the exception
            return ResponseEntity.status(500).body(null);
        }
    }
}
