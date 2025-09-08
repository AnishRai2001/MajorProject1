package com.example.BuyerSellerService.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class SellerProfile {

    @Id
    private Long userId; // same as UserService userId

    private String dealershipName;
    private String gstNumber;
    private String address;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CarEvaluationRequest> evaluationRequests = new ArrayList<>();

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDealershipName() { return dealershipName; }
    public void setDealershipName(String dealershipName) { this.dealershipName = dealershipName; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<CarEvaluationRequest> getEvaluationRequests() { return evaluationRequests; }
    public void setEvaluationRequests(List<CarEvaluationRequest> evaluationRequests) { this.evaluationRequests = evaluationRequests; }
}
