 package com.example.BuyerSellerService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BuyerSellerService.entity.CarEvaluationRequest;

public interface EvaluationRequestRepository extends JpaRepository<CarEvaluationRequest, Long> {

	List<CarEvaluationRequest> findBySellerUserId(Long sellerId);

	List<CarEvaluationRequest> findBySellerUserIdAndBuyerUserIdAndVehicleId(Long sellerId, Long buyerId,
			Long vehicleId);

}
