package com.onlinevoting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginInfo {

    @NotBlank
    @Email
    private String userId;
    
    public UserLoginInfo() {
    }

    public UserLoginInfo(@NotBlank @Email String userId) {
         if (!userId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid user Id");
         } 
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
