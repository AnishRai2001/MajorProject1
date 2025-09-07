package com.example.BuyerSellerService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BuyerSellerService.entity.BuyerProfile;

public interface BuyerProfileRepository extends JpaRepository<BuyerProfile, Long> {
}

