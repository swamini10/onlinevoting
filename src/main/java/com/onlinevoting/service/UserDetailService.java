package com.onlinevoting.service;

import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.repository.UserDetailRepository;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {
     @Autowired
     private UserDetailRepository userDetailRepository;

     @Autowired
     private EmailService emailService;

     public UserDetail saveUser(UserDetail userDetail) {
          var emailId = userDetail.getEmailId();

          UserDetail existingUserDetail = userDetailRepository.findByEmailId(emailId);
          if (existingUserDetail != null) {
               throw new IllegalArgumentException("User with account for email " + emailId + " already exists.");
          }

          UserDetail userDetail1 = new UserDetail(userDetail.getFirstName(), userDetail.getLastName(),
                    userDetail.getMiddleName(), userDetail.getEmailId(), userDetail.getPhoneNo(),
                    userDetail.getAddress(),
                    userDetail.getDob(), userDetail.getAadharNumber(), userDetail.getPhoto());
          UserDetail uDetails = userDetailRepository.save(userDetail1);
          // Send welcome email
          try {
               emailService.sendEmailWithTemplate(userDetail.getEmailId(), EmailConstants.WELCOME_SUBJECT,
                         EmailConstants.USER_CREATE_TEMPLATE, Map.of("name", userDetail.getFirstName()));
          } catch (Exception e) {
               e.printStackTrace();
          }
          return uDetails;
     }

     public UserDetail getUserByEmail(String email) {
          return userDetailRepository.findByEmailId(email);
     }

     public UserDetail updateUser(UserDetail userDetail) {
          var id = userDetail.getId();

          Optional<UserDetail> existingUserDetail = userDetailRepository.findById(id);
          
          if (existingUserDetail.isEmpty()) {
               throw new IllegalArgumentException("User with account for ID " + id + " does not exist.");
          }

          UserDetail user = existingUserDetail.get();
          user.setFirstName(userDetail.getFirstName());
          user.setLastName(userDetail.getLastName());
          user.setMiddleName(userDetail.getMiddleName());
          user.setPhoneNo(userDetail.getPhoneNo());
          user.setAddress(userDetail.getAddress());
          user.setDob(userDetail.getDob());
          user.setAadharNumber(userDetail.getAadharNumber());
          user.setPhoto(userDetail.getPhoto());

          return userDetailRepository.save(user);
          
     }
}
