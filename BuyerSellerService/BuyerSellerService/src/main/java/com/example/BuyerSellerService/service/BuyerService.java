package com.example.BuyerSellerService.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.BuyerSellerService.Dto.UserDto;
import com.example.BuyerSellerService.Repository.BuyerProfileRepository;
import com.example.BuyerSellerService.Repository.Wishlistepository;
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

    // Save buyer preferences
    public BuyerProfile savePreferences(Long userId, BuyerProfile profile) {
        // fetch user from UserService
        UserDto user = userServiceClient.getUserById(userId);
        if (!user.getRole().equals("BUYER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a buyer!");
        }

        profile.setUserId(userId);
        return buyerRepository.save(profile);
    }

    // Add vehicle to wishlist
    public Wishlist addToWishlist(Long userId, Long vehicleId) {
        UserDto user = userServiceClient.getUserById(userId);
        if (!user.getRole().equals("BUYER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a buyer!");
        }

        BuyerProfile buyer = buyerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Buyer profile not found!"));

        Wishlist wishlist = new Wishlist();
        wishlist.setBuyer(buyer);
        wishlist.setVehicleId(vehicleId);
        return wishlistepository.save(wishlist);
    }

    // Get wishlist
    public List<Wishlist> getWishlist(Long userId) {
        UserDto user = userServiceClient.getUserById(userId);
        if (!user.getRole().equals("BUYER") && !user.getRole().equals("BOTH")) {
            throw new RuntimeException("User is not allowed to act as a buyer!");
        }

        BuyerProfile buyer = buyerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Buyer profile not found!"));

        return buyer.getWishlist();
    }
}
