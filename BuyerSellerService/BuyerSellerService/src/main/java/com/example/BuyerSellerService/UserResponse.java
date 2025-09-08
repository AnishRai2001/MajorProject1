package com.example.BuyerSellerService;

import com.example.BuyerSellerService.Dto.UserDto;

public class UserResponse {
    private boolean success;
    private String message;
    private UserDto data;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UserDto getData() { return data; }
    public void setData(UserDto data) { this.data = data; }
}
