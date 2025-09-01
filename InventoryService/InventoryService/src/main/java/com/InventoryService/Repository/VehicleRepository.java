package com.InventoryService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.InventoryService.entity.VehicleDetails;

public interface VehicleRepository extends JpaRepository<VehicleDetails, Long>{

}
