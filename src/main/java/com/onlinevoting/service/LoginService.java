
package com.onlinevoting.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.dto.UserLoginDTO;
import com.onlinevoting.dto.UserLoginInfo;
import com.onlinevoting.exception.UserNotFoundException;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.model.UserOtpDetails;
import com.onlinevoting.repository.UserDetailRepository;
import com.onlinevoting.repository.UserOtpDetailsRepository;
import com.onlinevoting.util.OtpUtil;


@Service
public class LoginService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserOtpDetailsRepository userOtpDetailsRepository;
    
    @Autowired
    private EmailService emailService;

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    /**
     * Generate OTP for the given user login info.
     * @param userLoginInfo
     * @throws IllegalArgumentException if the userId is not a valid email format. 
     * UserId not found in database throw custom exception (UserNotFoundException)
     * if user is found, generate OTP and send it to the user's email address.
     */
    public void generateOtp(UserLoginInfo userLoginInfo) {
    logger.info("LoginService initialized");
     UserDetail userDetail = userDetailRepository.findByEmailId(userLoginInfo.getUserId());
        if (userDetail == null) {
            throw new UserNotFoundException("User not found with email: " + userLoginInfo.getUserId());
        }
        // Generate a 5-digit OTP
        Integer otp = OtpUtil.generateOtp();
        logger.info("Generated OTP: " + otp + " for user: " + userLoginInfo.getUserId());
        // Save the OTP to the user's record
        userOtpDetailsRepository.save(new UserOtpDetails(userDetail, otp));
        // Here, you would typically send the OTP to the user's email address.
       HashMap<String, Object> userOtpMap = new HashMap<String, Object>();
       userOtpMap.put("name", userDetail.fullName());
       userOtpMap.put("otp", otp.toString());
        try {
            emailService.sendEmailWithTemplate(userLoginInfo.getUserId(), EmailConstants.OTP_SUBJECT,
                    EmailConstants.OTP_TEMPLATE, userOtpMap );
            logger.info("OTP email sent to: " + userLoginInfo.getUserId());
        } catch (Exception e) {
            logger.error("Failed to send OTP email to: " + userLoginInfo.getUserId()  , e);
        }
    }

    /**
     * Login user with the given user login info.
     * @param userLoginInfo
     * @return a dummy JWT token if login is successful.
     * @throws UserNotFoundException if the userId is not found in the database.
     * @throws IllegalArgumentException if the OTP is invalid.
     */

    public boolean loginUser(UserLoginDTO userLoginInfoDto) {
        UserDetail userDetail = userDetailRepository.findByEmailId(userLoginInfoDto.getUserId());
        if (userDetail == null) {
            throw new UserNotFoundException("User not found with email: " + userLoginInfoDto.getUserId());
        }
        Optional<UserOtpDetails> userOtpDetails = userOtpDetailsRepository.findByUserDetailIdAndIsOtpUsedFalse(userDetail.getId());
      
        if (userOtpDetails == null || !userOtpDetails.isPresent()) {
            throw new IllegalArgumentException("No valid OTP found for user: " + userLoginInfoDto.getUserId());
        }
        if (userOtpDetails.get().getOtp().toString().equals(userLoginInfoDto.getOtp())) {
            userOtpDetails.get().setOtpUsed(true);
            userOtpDetailsRepository.save(userOtpDetails.get());
            logger.info("User logged in successfully: " + userLoginInfoDto.getUserId());
            return true;
        }
        throw new IllegalArgumentException("Invalid OTP for user: " + userLoginInfoDto.getUserId());
    }
}
