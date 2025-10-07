package com.onlinevoting.constants;

public interface Constants {

     static final String USER_NOT_FOUND = "User not found with email: ";
     static final String OTP_ALREADY_SENT = "Otp already sent please check your email to login.";
     static final String OTP_EXPIRED = "Otp expired please generate a new one.";
     static final String USER_NOT_ACTIVE = "User not active please contact admin.";
     static final String INVALID_OTP = "Invalid OTP.";
     static final String OTP_SENT_SUCCESS = "Otp sent successfully to your email.";
     static final String USER_LOGIN_SUCCESS = "User logged in successfully.";
     static final String USER_NOT_ACTIVE_MESSAGE = "Your account has been created but is not yet active. Please wait for document verification and officer approval. Your account will be activated within 24 hours after approval.";
     static final String USER_REGISTRATION_TEMPLATE = "user_create_success.ftl";
     static final String SUBJECT = "Registration Successful";
}
