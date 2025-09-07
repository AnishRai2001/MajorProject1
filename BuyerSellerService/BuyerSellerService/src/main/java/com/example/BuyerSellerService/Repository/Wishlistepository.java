package com.example.BuyerSellerService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BuyerSellerService.entity.Wishlist;

public interface Wishlistepository  extends JpaRepository<Wishlist, Long>{

}
