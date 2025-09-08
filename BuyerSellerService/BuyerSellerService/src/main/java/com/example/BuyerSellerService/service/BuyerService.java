package com.example.BuyerSellerService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.BuyerSellerService.Dto.UserDto;
import com.example.BuyerSellerService.Dto.VehicleDto;
import com.example.BuyerSellerService.Repository.BuyerProfileRepository;
import com.example.BuyerSellerService.Repository.Wishlistepository;
import com.example.BuyerSellerService.client.InventoryClient;
import com.example.BuyerSellerService.client.UserServiceClient;
import com.example.BuyerSellerService.entity.BuyerProfile;
import com.example.BuyerSellerService.entity.Wishlist;

@Service
public class BuyerService {

    @Autowired
    private BuyerProfileRepository buyerRepository;

    @Autowired
    private Wishlistepository wishlistepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private InventoryClient inventoryClient;

    // Save buyer preferences (create or update)
    public BuyerProfile savePreferences(Long userId, BuyerProfile profile) {
        UserDto user = userServiceClient.getUserById(userId);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in UserService");
        }

        String role = (user.getRole() != null) ? user.getRole().trim().toUpperCase() : "";
        if (!"BUYER".equals(role) && !"BOTH".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to act as a buyer");
        }

        profile.setUserId(userId);

        // update if exists
        BuyerProfile existing = buyerRepository.findById(userId).orElse(null);
        if (existing != null) {
            existing.setPreferredBrand(profile.getPreferredBrand());
            existing.setPreferredFuelType(profile.getPreferredFuelType());
            existing.setMinBudget(profile.getMinBudget());
            existing.setMaxBudget(profile.getMaxBudget());
            existing.setLocation(profile.getLocation());
            if (existing.getWishlist() == null) existing.setWishlist(new ArrayList<>());
            return buyerRepository.save(existing);
        }

        if (profile.getWishlist() == null) profile.setWishlist(new ArrayList<>());
        return buyerRepository.save(profile);
    }

    // Add vehicle to wishlist
    public Wishlist addToWishlist(Long userId, Long vehicleId) {
        UserDto user = userServiceClient.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in UserService");
        }

        String role = (user.getRole() != null) ? user.getRole().trim().toUpperCase() : "";
        if (!"BUYER".equals(role) && !"BOTH".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to act as a buyer");
        }

        BuyerProfile buyer = buyerRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buyer profile not found"));

        VehicleDto v = inventoryClient.getVehicleById(vehicleId);
        if (v == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found in Inventory");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setBuyer(buyer);
        wishlist.setVehicleId(vehicleId);
        return wishlistepository.save(wishlist);
    }

    // Get wishlist with vehicle details
    public List<VehicleDto> getWishlist(Long userId) {
        UserDto user = userServiceClient.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in UserService");
        }

        String role = (user.getRole() != null) ? user.getRole().trim().toUpperCase() : "";
        if (!"BUYER".equals(role) && !"BOTH".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to act as a buyer");
        }

        BuyerProfile buyer = buyerRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buyer profile not found"));

        List<VehicleDto> vehicleList = new ArrayList<>();
        if (buyer.getWishlist() != null) {
            for (Wishlist wishlist : buyer.getWishlist()) {
                VehicleDto vehicle = inventoryClient.getVehicleById(wishlist.getVehicleId());
                if (vehicle != null) {
                    vehicleList.add(vehicle);
                }
            }
        }
        return vehicleList;
    }
}
