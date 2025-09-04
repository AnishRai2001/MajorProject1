package com.InventoryService.controller;

import com.InventoryService.ResponseStructure.PageResponse;
import com.InventoryService.dto.VehicleDetailsDto;
import com.InventoryService.service.VehicleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VehicleDetailsDto> createVehicle(
            @RequestPart("vehicle") String vehicleJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        VehicleDetailsDto vehicleDto = mapper.readValue(vehicleJson, VehicleDetailsDto.class);

        // ✅ call service to persist
        VehicleDetailsDto saved = vehicleService.createVehicle(vehicleDto, images);

        // ✅ return saved DTO (with ID, mediaUrls, etc.)
        return ResponseEntity.ok(saved);
    }



    @GetMapping("/{id}")
    public ResponseEntity<VehicleDetailsDto> getVehicle(@PathVariable Long id) {
        return vehicleService.findVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<VehicleDetailsDto> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDetailsDto> updateVehicle(@PathVariable Long id,
                                                           @RequestBody VehicleDetailsDto dto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<PageResponse<VehicleDetailsDto>> searchVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String transmissionType,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
            vehicleService.searchVehiclesPageResponse(brand, model, minPrice, maxPrice, transmissionType, pageable)
        );
    }


}
