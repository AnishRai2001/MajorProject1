package com.InventoryService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.InventoryService.dto.MediaUploadResponse;

@FeignClient(name = "media-service", url = "http://localhost:8083")
public interface MediaClient {

    @PostMapping(value = "/media/upload", consumes = "multipart/form-data")
    MediaUploadResponse uploadMedia(@RequestParam("vehicleId") Long vehicleId,
                                    @RequestPart("file") MultipartFile file);
}
