package com.example.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BookingService.entity.dto.VehicleDetailsDto;

@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryClient {
    @GetMapping("/vehicles/{id}")   // ❌ remove /api if your controller does not have it
    VehicleDetailsDto getVehicleById(@PathVariable Long id);
}



