package com.example.BuyerSellerService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BuyerSellerService.entity.SellerProfile;

public interface SellerRepository extends JpaRepository<SellerProfile, Long> {

}
