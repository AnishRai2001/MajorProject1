package com.example.BuyerSellerService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BuyerSellerService.Dto.UserDto;
import com.example.BuyerSellerService.Repository.EvaluationRequestRepository;
import com.example.BuyerSellerService.Repository.SellerRepository;
import com.example.BuyerSellerService.client.UserServiceClient;
import com.example.BuyerSellerService.entity.CarEvaluationRequest;
import com.example.BuyerSellerService.entity.SellerProfile;

@Service
public class SellerService {

    private final SellerRepository sellerRepo;
    private final EvaluationRequestRepository evalRepo;
    private final UserServiceClient userServiceClient;

    public SellerService(SellerRepository sellerRepo,
                         EvaluationRequestRepository evalRepo,
                         UserServiceClient userServiceClient) {
        this.sellerRepo = sellerRepo;
        this.evalRepo = evalRepo;
        this.userServiceClient = userServiceClient;
    }

    private void validateSellerRole(Long userId) {
        UserDto user = userServiceClient.getUserById(userId);
        String role = user.getRole();
        if (role == null || (!role.equals("SELLER") && !role.equals("BOTH"))) {
            throw new RuntimeException("User is not allowed to act as a seller!");
        }
    }

    public SellerProfile saveSellerProfile(Long userId, SellerProfile profile) {
        validateSellerRole(userId);
        profile.setUserId(userId);
        return sellerRepo.save(profile);
    }

    public CarEvaluationRequest requestEvaluation(Long sellerId, Long buyerId, Long vehicleId) {
        validateSellerRole(sellerId);

        SellerProfile seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller profile not found!"));
        List<CarEvaluationRequest> existing = evalRepo.findBySellerUserIdAndBuyerUserIdAndVehicleId(
                sellerId, buyerId, vehicleId);
        if (!existing.isEmpty()) {
            throw new RuntimeException("Evaluation request already exists!");
        }

        CarEvaluationRequest request = new CarEvaluationRequest();
        request.setSellerUserId(seller.getUserId());
        request.setBuyerUserId(buyerId);
        request.setVehicleId(vehicleId);
        request.setStatus("PENDING");

        return evalRepo.save(request);
    }

    public SellerProfile getSellerProfile(Long userId) {
        validateSellerRole(userId);
        return sellerRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Seller profile not found!"));
    }

    public List<CarEvaluationRequest> getSellerEvaluations(Long sellerId) {
        validateSellerRole(sellerId);
        return evalRepo.findBySellerUserId(sellerId);
    }
}
