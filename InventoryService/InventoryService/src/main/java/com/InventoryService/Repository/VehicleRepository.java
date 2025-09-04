package com.InventoryService.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.InventoryService.entity.VehicleDetails;

public interface VehicleRepository extends JpaRepository<VehicleDetails, Long> {

    @Query("SELECT v FROM VehicleDetails v " +
           "WHERE (:brand IS NULL OR v.brand.brand = :brand) " +
           "AND (:model IS NULL OR v.model.model = :model) " +
           "AND (:minPrice IS NULL OR v.price.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR v.price.price <= :maxPrice) " +
           "AND (:transmissionType IS NULL OR v.transmission_Type.transmission_type = :transmissionType)")
    Page<VehicleDetails> searchVehicles(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("transmissionType") String transmissionType,
            Pageable pageable
    );
}
