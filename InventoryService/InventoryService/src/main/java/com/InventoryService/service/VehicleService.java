package com.InventoryService.service;

import java.util.List;
import java.util.Optional;

import com.InventoryService.dto.VehicleDetailsDto;
import com.InventoryService.entity.VehicleDetails;

public interface VehicleService {
	   Optional<VehicleDetailsDto> findVehicleById(Long id);
	    List<VehicleDetailsDto> getAllVehicles();
	  VehicleDetails updateVehicle(Long id, VehicleDetailsDto dto);
	    void deleteVehicle(Long id);
		VehicleDetailsDto createVehicle(VehicleDetailsDto vehicle);

}
