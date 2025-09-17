package com.onlinevoting.service;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

        Optional<UserOtpDetails>  existingUserOtpDetails =  
                                              userOtpDetailsRepository.findByUserDetailIdAndIsOtpUsedFalseAndIsActiveTrue(userDetail.getId());
         
        if ( existingUserOtpDetails.isPresent() && !isOtpExpired(existingUserOtpDetails.get().getExpiryTime())) {
            throw new IllegalArgumentException("Otp already sent please check your email to login.");
         } else {

            // Generate a 5-digit OTP`
            Integer otp = OtpUtil.generateOtp();
            logger.info("Generated OTP: " + otp + " for user: " + userLoginInfo.getUserId());

            // Save the OTP to the user's record
           UserOtpDetails newUserOtpDetails =  new UserOtpDetails(userDetail, otp);
           newUserOtpDetails.setActive(true);
           newUserOtpDetails.setExpiryTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")).atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli()); // Indian time           
           userOtpDetailsRepository.save(newUserOtpDetails);
           
           // to send the email
           sentEmailToUser(userLoginInfo, userDetail, otp);
         }

    }

    private void sentEmailToUser(UserLoginInfo userLoginInfo, UserDetail userDetail, Integer otp) {
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

    private boolean isOtpExpired(Long expiryTime) {
        LocalDateTime currentDateTime  = LocalDateTime.now();
        LocalDateTime exDateTime  = LocalDateTime.ofInstant(Instant.ofEpochMilli(expiryTime), ZoneId.systemDefault());
       long diffMinutes = Duration.between(exDateTime,currentDateTime ).toMinutes();
       return Long.valueOf(diffMinutes) >= 5;
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
        
        Optional<UserOtpDetails> userOtpDetails = userOtpDetailsRepository.findByUserDetailIdAndIsOtpUsedFalseAndIsActiveTrue(userDetail.getId());
      
        if (userOtpDetails == null || !userOtpDetails.isPresent()) {
            throw new IllegalArgumentException("No valid OTP found for user: " + userLoginInfoDto.getUserId());
        }
        
        if (userOtpDetails.get().getOtp().toString().equals(userLoginInfoDto.getOtp()) && !isOtpExpired(userOtpDetails.get().getExpiryTime())) {
            userOtpDetails.get().setOtpUsed(true);
            userOtpDetails.get().setActive(false);
            userOtpDetailsRepository.save(userOtpDetails.get());
            logger.info("User logged in successfully: " + userLoginInfoDto.getUserId());
            return true;
        }
        throw new IllegalArgumentException("Invalid OTP for user: " + userLoginInfoDto.getUserId());
    }
}
