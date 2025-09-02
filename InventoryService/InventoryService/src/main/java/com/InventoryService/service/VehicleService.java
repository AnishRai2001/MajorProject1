package com.InventoryService.service;

import com.InventoryService.dto.VehicleDetailsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

	public   VehicleDetailsDto createVehicle(VehicleDetailsDto dto, List<MultipartFile> files) ;

    Optional<VehicleDetailsDto> findVehicleById(Long id);

    List<VehicleDetailsDto> getAllVehicles();

    VehicleDetailsDto updateVehicle(Long id, VehicleDetailsDto dto);

    void deleteVehicle(Long id);
}
