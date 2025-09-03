package com.example.mediaService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.mediaService.Dto.MediaUploadResponse;

@FeignClient(name = "media-service")
public interface MediaClient {

    @PostMapping(value = "/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    MediaUploadResponse uploadMedia(@RequestParam("vehicleId") Long vehicleId,
                                    @RequestPart("file") MultipartFile file);
}

