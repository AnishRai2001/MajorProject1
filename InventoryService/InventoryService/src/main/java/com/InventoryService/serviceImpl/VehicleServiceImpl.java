package com.InventoryService.serviceImpl;

import com.InventoryService.Repository.VehicleRepository;
import com.InventoryService.ResponseStructure.PageResponse;
import com.InventoryService.client.MediaClient;
import com.InventoryService.dto.MediaUploadResponse;
import com.InventoryService.dto.VehicleDetailsDto;
import com.InventoryService.entity.*;
import com.InventoryService.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable; // ✅ correct
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MediaClient mediaClient;

    @Override
    public VehicleDetailsDto createVehicle(VehicleDetailsDto dto, List<MultipartFile> files) {
        // Step 1: Convert DTO -> Entity
        VehicleDetails vehicle = new VehicleDetails();

        Brand brand = new Brand();//You are creating a new instance of the Brand entity
        brand.setBrand(dto.getBrand());//save brand from dto to entity
        vehicle.setBrand(brand);

        Moddel model = new Moddel();
        model.setModel(dto.getModel());
        vehicle.setModel(model);

        Price price = new Price();
        price.setPrice(dto.getPrice());
        vehicle.setPrice(price);

        Mileage mileage = new Mileage();
        mileage.setMileage(dto.getMileage());
        vehicle.setMileage(mileage);

        Year year = new Year();
        year.setYear(dto.getYear());
        vehicle.setYear(year);

        Transmission_Type transmissionType = new Transmission_Type();
        transmissionType.setTransmission_type(dto.getTransmissionType());
        vehicle.setTransmission_Type(transmissionType);

        // ✅ Save vehicle first (to get ID)
        VehicleDetails savedVehicle = vehicleRepository.save(vehicle);

        // Step 2: Upload media (if any)
        List<VehicleMedia> mediaList = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                MediaUploadResponse response = mediaClient.uploadMedia(savedVehicle.getId(), file);

                VehicleMedia media = new VehicleMedia();
                media.setS3Url(response.getS3Url());
                media.setVehicleDetails(savedVehicle);
                savedVehicle.getMediaList().add(media);
            }
        }

        savedVehicle = vehicleRepository.save(savedVehicle);

        // Step 3: Convert back to DTO
        return convertToDto(savedVehicle);
    }

    @Override
    public Optional<VehicleDetailsDto> findVehicleById(Long id) {
        Optional<VehicleDetails> optional = vehicleRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(convertToDto(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<VehicleDetailsDto> getAllVehicles() {
        List<VehicleDetails> vehicles = vehicleRepository.findAll();
        List<VehicleDetailsDto> dtoList = new ArrayList<>();
        for (VehicleDetails v : vehicles) {
            dtoList.add(convertToDto(v));
        }
        return dtoList;
    }

    @Override
    public VehicleDetailsDto updateVehicle(Long id, VehicleDetailsDto dto) {
        VehicleDetails vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));

        if (dto.getBrand() != null) vehicle.getBrand().setBrand(dto.getBrand());
        if (dto.getModel() != null) vehicle.getModel().setModel(dto.getModel());
        if (dto.getPrice() != null) vehicle.getPrice().setPrice(dto.getPrice());
        if (dto.getMileage() != null) vehicle.getMileage().setMileage(dto.getMileage());
        if (dto.getYear() != null) vehicle.getYear().setYear(dto.getYear());
        if (dto.getTransmissionType() != null) vehicle.getTransmission_Type().setTransmission_type(dto.getTransmissionType());

        VehicleDetails updated = vehicleRepository.save(vehicle);

        return convertToDto(updated);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id " + id);
        }
        vehicleRepository.deleteById(id);
    }

    // ✅ Utility method
    private VehicleDetailsDto convertToDto(VehicleDetails vehicle) {
        VehicleDetailsDto dto = new VehicleDetailsDto();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand().getBrand());
        dto.setModel(vehicle.getModel().getModel());
        dto.setPrice(vehicle.getPrice().getPrice());
        dto.setMileage(vehicle.getMileage().getMileage());
        dto.setYear(vehicle.getYear().getYear());
        dto.setTransmissionType(vehicle.getTransmission_Type().getTransmission_type());

        List<String> urls = new ArrayList<>();
        if (vehicle.getMediaList() != null) {
            for (VehicleMedia media : vehicle.getMediaList()) {
                urls.add(media.getS3Url());
            }
        }
        dto.setMediaUrls(urls);

        return dto;
    }

    @Override
    public PageResponse<VehicleDetailsDto> searchVehiclesPageResponse(
            String brand,
            String model,
            Double minPrice,
            Double maxPrice,
            String transmissionType,
            Pageable pageable
    ) {
        Page<VehicleDetails> page = vehicleRepository.searchVehicles(
                brand, model, minPrice, maxPrice, transmissionType, pageable
        );

        List<VehicleDetailsDto> content = new ArrayList<>();
        for (VehicleDetails v : page.getContent()) {
            content.add(convertToDto(v));
        }

        PageResponse<VehicleDetailsDto> resp = new PageResponse<>();
        resp.setContent(content);
        resp.setPage(page.getNumber());
        resp.setSize(page.getSize());
        resp.setTotalElements(page.getTotalElements());
        resp.setTotalPages(page.getTotalPages());
        resp.setLast(page.isLast());
        return resp;
    }

//	@Override
//	public PageResponse<VehicleDetailsDto> searchVehiclesPageResponse(String brand, String model, Double minPrice,
//			Double maxPrice, String transmissionType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
