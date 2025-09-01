package com.InventoryService.serviceImpl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InventoryService.Repository.VehicleRepository;
import com.InventoryService.dto.VehicleDetailsDto;
import com.InventoryService.entity.Brand;
import com.InventoryService.entity.Mileage;
import com.InventoryService.entity.Moddel;
import com.InventoryService.entity.Price;
import com.InventoryService.entity.VehicleDetails;
import com.InventoryService.service.VehicleService;
import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public VehicleDetailsDto createVehicle(VehicleDetailsDto vehicledto) {

	    VehicleDetails details = new VehicleDetails();

	    // Brand
	    Brand brand = new Brand();
	    brand.setBrand(vehicledto.getBrand());
	    details.setBrand(brand);

	    // Model
	    Moddel model = new Moddel();
	    model.setModel(vehicledto.getModel());
	    details.setModel(model);

	    // Price
	    Price price = new Price();
	    price.setPrice(vehicledto.getPrice());
	    details.setPrice(price);

	    // Mileage
	    Mileage mileage = new Mileage();
	    mileage.setMileage(vehicledto.getMileage());
	    details.setMileage(mileage);

	    // Year
	    com.InventoryService.entity.Year years = new com.InventoryService.entity.Year();
	    years.setYear(vehicledto.getYear());
	    details.setYear(years);

	    // Save entity
	    VehicleDetails saved = vehicleRepository.save(details);

	    // Convert back to DTO
	    VehicleDetailsDto response = new VehicleDetailsDto();
	    response.setId(saved.getId());
	    response.setBrand(saved.getBrand().getBrand());
	    response.setModel(saved.getModel().getModel());
	    response.setPrice(saved.getPrice().getPrice());
	    response.setMileage(saved.getMileage().getMileage());
	    response.setYear(saved.getYear().getYear());

	    return response;
	}

	@Override
	public Optional<VehicleDetailsDto> findVehicleById(Long id) {
	    Optional<VehicleDetails> vehicleOpt = vehicleRepository.findById(id);

	    if (vehicleOpt.isEmpty()) {
	        return Optional.empty();
	    }

	    VehicleDetails vehicle = vehicleOpt.get();
	    VehicleDetailsDto dto = new VehicleDetailsDto();
	    dto.setId(vehicle.getId());
	    dto.setBrand(vehicle.getBrand().getBrand());
	    dto.setModel(vehicle.getModel().getModel());
	    dto.setPrice(vehicle.getPrice().getPrice());
	    dto.setMileage(vehicle.getMileage().getMileage());
	    dto.setYear(vehicle.getYear().getYear());

	    return Optional.of(dto);
	}

	@Override
	public List<VehicleDetailsDto> getAllVehicles() {
	    List<VehicleDetails> vehicles = vehicleRepository.findAll();
	    List<VehicleDetailsDto> dtos = new ArrayList<>();

	    for (VehicleDetails vehicle : vehicles) {
	        VehicleDetailsDto dto = new VehicleDetailsDto();
	        dto.setId(vehicle.getId());
	        dto.setBrand(vehicle.getBrand().getBrand());
	        dto.setModel(vehicle.getModel().getModel());
	        dto.setPrice(vehicle.getPrice().getPrice());
	        dto.setMileage(vehicle.getMileage().getMileage());
	        dto.setYear(vehicle.getYear().getYear());
	        dtos.add(dto);
	    }

	    return dtos;
	}

	@Override
	public VehicleDetails updateVehicle(Long id, VehicleDetailsDto dto) {
	    VehicleDetails vehicle = vehicleRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));

	    // Update Brand
	    if (vehicle.getBrand() != null) {
	        vehicle.getBrand().setBrand(dto.getBrand());
	    }

	    // Update Model
	    if (vehicle.getModel() != null) {
	        vehicle.getModel().setModel(dto.getModel());
	    }

	    // Update Price
	    if (vehicle.getPrice() != null) {
	        vehicle.getPrice().setPrice(dto.getPrice());
	    }

	    // Update Mileage
	    if (vehicle.getMileage() != null) {
	        vehicle.getMileage().setMileage(dto.getMileage());
	    }

	    // Update Year
	    if (vehicle.getYear() != null) {
	        vehicle.getYear().setYear(dto.getYear());
	    }

	    return vehicleRepository.save(vehicle);
	}

	
	
	
	@Override
	public void deleteVehicle(Long id) {
	    if (!vehicleRepository.existsById(id)) {
	        throw new RuntimeException("Vehicle not found with id " + id);
	    }
	    vehicleRepository.deleteById(id);
	}

}
