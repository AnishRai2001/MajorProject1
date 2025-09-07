package com.example.BuyerSellerService.service;

import com.example.BuyerSellerService.Dto.UserDto;
import com.example.BuyerSellerService.Repository.EvaluationRequestRepository;
import com.example.BuyerSellerService.Repository.SellerRepository;
import com.example.BuyerSellerService.client.UserServiceClient;
import com.example.BuyerSellerService.entity.CarEvaluationRequest;
import com.example.BuyerSellerService.entity.SellerProfile;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // Save seller profile
    public SellerProfile saveSellerProfile(Long userId, SellerProfile profile) {
        UserDto user = userServiceClient.getUserById(userId);
        if (!user.getRole().equals("SELLER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a seller!");
        }
        profile.setUserId(userId);
        return sellerRepo.save(profile);
    }

    // Request evaluation
    public CarEvaluationRequest requestEvaluation(Long sellerId, Long buyerId, Long vehicleId) {
        UserDto sellerUser = userServiceClient.getUserById(sellerId);
        if (!sellerUser.getRole().equals("SELLER") && !sellerUser.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a seller!");
        }

        SellerProfile seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller profile not found!"));

        CarEvaluationRequest request = new CarEvaluationRequest();
        request.setSellerUserId(seller.getUserId());
        request.setBuyerUserId(buyerId);
        request.setVehicleId(vehicleId);
        request.setStatus("PENDING");
        request.setSellerUserId(seller.getUserId());

        return evalRepo.save(request);
    }

    // Fetch seller profile
    public SellerProfile getSellerProfile(Long userId) {
        UserDto user = userServiceClient.getUserById(userId);
        if (!user.getRole().equals("SELLER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a seller!");
        }

        return sellerRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Seller profile not found!"));
    }

    // Fetch all evaluations for a seller
    public List<CarEvaluationRequest> getSellerEvaluations(Long sellerId) {
        UserDto user = userServiceClient.getUserById(sellerId);
        if (!user.getRole().equals("SELLER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a seller!");
        }

        return evalRepo.findBySellerUserId(sellerId);
    }
}
