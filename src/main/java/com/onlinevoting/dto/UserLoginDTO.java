package com.onlinevoting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank
    @Email
    private String userId;

    @NotBlank
    private String otp;

    public UserLoginDTO() {
    }

    public UserLoginDTO(@NotBlank @Email String userId, @NotBlank String otp) {
        if (!userId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid user Id");
         } 
        if (otp == null || otp.isBlank()  | otp.length() !=5) {
            throw new IllegalArgumentException("OTP is required");
         } 
        this.userId = userId;
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }

}
