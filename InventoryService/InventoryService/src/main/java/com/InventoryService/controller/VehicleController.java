package com.InventoryService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.InventoryService.dto.VehicleDetailsDto;
import com.InventoryService.entity.VehicleDetails;
import com.InventoryService.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Create vehicle
    @PostMapping
    public ResponseEntity<VehicleDetailsDto> createVehicle(@RequestBody VehicleDetailsDto vehicleDto) {
        VehicleDetailsDto created = vehicleService.createVehicle(vehicleDto);
        return ResponseEntity.ok(created);
    }

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<VehicleDetailsDto>> getAllVehicles() {
        List<VehicleDetailsDto> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDetailsDto> getVehicleById(@PathVariable Long id) {
        Optional<VehicleDetailsDto> vehicleOpt = vehicleService.findVehicleById(id);
        return vehicleOpt.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // Update vehicle
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDetails> updateVehicle(@PathVariable Long id, @RequestBody VehicleDetailsDto dto) {
        VehicleDetails updated = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
