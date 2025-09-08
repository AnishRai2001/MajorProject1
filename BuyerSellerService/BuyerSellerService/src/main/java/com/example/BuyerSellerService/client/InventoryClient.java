package com.example.BuyerSellerService.client;

import com.example.BuyerSellerService.Dto.VehicleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryClient {

    @GetMapping("/vehicles/{id}")
    VehicleDto getVehicleById(@PathVariable("id") Long id);
}
