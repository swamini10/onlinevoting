package com.onlinevoting.service;

import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.dto.UserDetailDTO;
import com.onlinevoting.dto.UserProfileUpdateDTO;
import com.onlinevoting.enums.Status;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.repository.UserDetailRepository;
import com.onlinevoting.util.UserContextHelper;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailService {

     @Autowired
     private UserDetailRepository userDetailRepository;

     @Autowired
     private EmailService emailService;

     private UserContextHelper userContextHelper;

     public UserDetail saveUser(UserDetail userDetail) {
          var emailId = userDetail.getEmailId();

          UserDetail existingUserDetail = userDetailRepository.findByEmailId(emailId);
          if (existingUserDetail != null) {
               throw new IllegalArgumentException("User with account for email " + emailId + " already exists.");
          }

          UserDetail newUserDetail = new UserDetail(userDetail.getFirstName(), userDetail.getLastName(),
                    userDetail.getMiddleName(), userDetail.getEmailId(), userDetail.getPhoneNo(),
                    userDetail.getAddress(),
                    userDetail.getDob(), userDetail.getAadharNumber(), userDetail.getDocsUrl(), userDetail.getRole());

          newUserDetail.setActive(false);
          newUserDetail.setStatus(Status.PENDING.getDisplayName());

          UserDetail uDetails = userDetailRepository.save(newUserDetail);
          // Send welcome email
          try {
               emailService.sendEmailWithTemplate(userDetail.getEmailId(), EmailConstants.WELCOME_SUBJECT,
                         EmailConstants.USER_CREATE_TEMPLATE, Map.of("name", userDetail.getFirstName()));
          } catch (Exception e) {
               e.printStackTrace();
          }
          return uDetails;
     }

     public List<UserDetail> findUsersByPhone(String phone) {
          return userDetailRepository.findByPhoneNo(phone);
     }


     public List<UserDetail> findUsersByEmail(String email) {
          return userDetailRepository.findByEmail(email);
     }
     
     public void approveUser(Long id, String status) {

          if (status == null || (!status.equals(Status.APPROVED.getDisplayName()) && !status.equals(Status.REJECTED.getDisplayName()))) {
               throw new IllegalArgumentException("Invalid status value. Only 'APPROVED' or 'REJECTED' are allowed.");
          }

          Optional<UserDetail> existingUserDetail = userDetailRepository.findById(id);
          
          if (existingUserDetail.isEmpty()) {
               throw new IllegalArgumentException("User with account for ID " + id + " does not exist.");
          }

          UserDetail userDetail = existingUserDetail.get();
          
          if (status.equals(Status.APPROVED.getDisplayName())) {
               userDetail.setActive(true);
               userDetail.setStatus(status);
               userDetailRepository.save(userDetail);
               sendAccountActivationEmail(userDetail.getEmailId(), userDetail.getFirstName(), userDetail.getEmailId(), "http://localhost:8080/login");
          } else if (status.equals(Status.REJECTED.getDisplayName())) {
               userDetail.setActive(false);
               userDetail.setStatus(status);
               userDetailRepository.save(userDetail);
               sendAccountRejectedEmail(userDetail.getEmailId(), userDetail.getFirstName(), userDetail.getEmailId(), "http://localhost:8080/help");
          } 
     }
     
     private void sendAccountRejectedEmail(String to, String name, String emailId, String helpUrl) {
          try {
               Map<String, Object> model = new HashMap<>();
               model.put("name", name);
               model.put("emailId", emailId);
               model.put("helpUrl", helpUrl);
               model.put("registrationDate", LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
               model.put("rejectionDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
               
               emailService.sendEmailWithTemplate(
                   to, 
                   EmailConstants.ACCOUNT_REJECTED_SUBJECT, 
                   EmailConstants.ACCOUNT_REJECTED_TEMPLATE, 
                   model
               );
          } catch (Exception e) {
               log.error("Failed to send account rejection email", e);
          }
     }
     
     private void sendAccountActivationEmail(String to, String name, String emailId, String loginUrl) {
    try {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("emailId", emailId);
        model.put("loginUrl", loginUrl);
        model.put("registrationDate", LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.put("approvalDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        emailService.sendEmailWithTemplate(
            to, 
            EmailConstants.ACCOUNT_ACTIVATED_SUBJECT, 
            EmailConstants.ACCOUNT_ACTIVATED_TEMPLATE, 
            model
        );
    } catch (Exception e) {
        log.error("Failed to send account activation email", e);
    }
     }

     public UserProfileUpdateDTO getUserProfile() {
          UserDetail  detail=  userDetailRepository.findByEmailId(userContextHelper.getCurrentUserEmail());
          if(detail == null) {
               throw new IllegalArgumentException("User with account for email " + userContextHelper.getCurrentUserEmail() + " does not exist.");
          }
          return mapToUserProfileUpdateDTO(detail);
     }

     public UserProfileUpdateDTO getUserProfileByEmail(String emailId) {
          UserDetail  detail=  userDetailRepository.findByEmailId(emailId);
          if(detail == null) {
               throw new IllegalArgumentException("User with account for email " + emailId + " does not exist.");
          }
          return mapToUserProfileUpdateDTO(detail);
     }

     private UserProfileUpdateDTO mapToUserProfileUpdateDTO(UserDetail detail) {
          UserProfileUpdateDTO profile = new UserProfileUpdateDTO();
          profile.setUserId(detail.getId());
          profile.setFirstName(detail.getFirstName());
          profile.setLastName(detail.getLastName());
          profile.setMiddleName(detail.getMiddleName());
          profile.setPhoneNo(detail.getPhoneNo());
          profile.setAddressId(detail.getAddress().getId());
          profile.setEmailId(detail.getEmailId());
          profile.setRoleId(detail.getRole().getId());
          profile.setCountryId(detail.getAddress().getCountryId().getId());
          profile.setStateId(detail.getAddress().getStateId().getId());
          profile.setCityId(detail.getAddress().getCityId().getId());
          profile.setDob(detail.getDob().toString());
          profile.setAadharNumber(detail.getAadharNumber().toString());
          profile.setDocsUrl(detail.getDocsUrl());
          profile.setStatus(detail.getStatus());
          profile.setStreet(detail.getAddress().getStreet());
          profile.setZipCode(detail.getAddress().getZipCode());
          profile.setProfileImageUrl(detail.getDocsUrl());
          // Set display names
          profile.setRoleName(detail.getRole().getName());
          profile.setCountryName(detail.getAddress().getCountryId().getName());
          profile.setStateName(detail.getAddress().getStateId().getName());
          profile.setCityName(detail.getAddress().getCityId().getName());
          
          return profile;
     }

     public UserDetail getUserByEmail(String email) {
          UserDetail  detail=  userDetailRepository.findByEmailId(email);
          if(detail == null) {
               throw new IllegalArgumentException("User with account for email " + email + " does not exist.");
          }
          return detail;
     }

     public UserDetail getUserById(Long id) {
          Optional<UserDetail> userDetail = userDetailRepository.findById(id);
          if (userDetail.isEmpty()) {
               throw new IllegalArgumentException("User with account for ID " + id + " does not exist.");
          }
          return userDetail.get();
     }

     public UserDetail updateUser(UserDetail userDetail) {
          String emailId = userDetail.getEmailId();

          UserDetail existingUserDetail = userDetailRepository.findByEmailId(emailId);
          
          if (existingUserDetail == null) {
               throw new IllegalArgumentException("User with account for email " + emailId + " does not exist.");
          }

          UserDetail user = existingUserDetail;
          user.setFirstName(userDetail.getFirstName());
          user.setLastName(userDetail.getLastName());
          user.setMiddleName(userDetail.getMiddleName());
          user.setPhoneNo(userDetail.getPhoneNo());
          user.setAddress(userDetail.getAddress());
          user.setDob(userDetail.getDob());
          user.setAadharNumber(userDetail.getAadharNumber());
          user.setDocsUrl(userDetail.getDocsUrl());
          user.setRole(userDetail.getRole());
          // TO Approve again after profile update
          user.setActive(false);
          user.setStatus(Status.PENDING.getDisplayName());
      
          UserDetail detail=  userDetailRepository.save(user);

          // Send update profile email
          try {
               emailService.sendEmailWithTemplate(userDetail.getEmailId(), EmailConstants.UPDATE_PROFILE_SUBJECT,
                         EmailConstants.UPDATE_PROFILE_TEMPLATE, Map.of("name", userDetail.getFirstName()));
          } catch (Exception e) {
               e.printStackTrace();
          }
          return detail;
     }

     public void deleteUser(Long id) {

          Optional<UserDetail> existingUserDetail = userDetailRepository.findByIdAndIsActiveTrue(id);
          
          if (existingUserDetail.isEmpty()) {
               throw new IllegalArgumentException("User with account for ID " + id + " does not exist.");
          }

          UserDetail userDetail = existingUserDetail.get();
          userDetail.setActive(false);
          userDetailRepository.save(userDetail);
     }

     public List<UserDetailDTO> getAllPendingApprovalUsers(String status, String orderBy, String order ) {
          List<UserDetailDTO> userDetails = new ArrayList<>();

          if(status == null) {
               throw new IllegalArgumentException("Status parameter is required.");
          } else if (status.equals(Status.APPROVED.getDisplayName())) {
               List<Object[]> newuserDetails = userDetailRepository.findByIsActiveAndStatus(Boolean.TRUE,status);
               for (Object[] obj : newuserDetails) {
                    userDetails.add(createUserDetailDTO(obj));
               }
          }else if (status.equals(Status.REJECTED.getDisplayName()) || status.equals(Status.PENDING.getDisplayName())) {
                  List<Object[]> newuserDetails = userDetailRepository.findByIsActiveAndStatus(Boolean.FALSE,status);
               for (Object[] obj : newuserDetails) {
                    userDetails.add(createUserDetailDTO(obj));
               }
          }

          return userDetails;
     }

      private UserDetailDTO createUserDetailDTO(Object[] obj) {
            String dobStr = null;
            if (obj[5] != null && obj[5] instanceof java.sql.Date) {
                 dobStr = obj[5].toString(); // or use a formatter if you want a specific format
            } else if (obj[5] != null) {
                 dobStr = obj[5].toString();
            }
            String adharStr = obj[6] != null ? obj[6].toString() : null;
            return new UserDetailDTO(
               String.valueOf(obj[0]), // id
                 (String) obj[1], // firstName
                 (String) obj[2], // lastName
                 (String) obj[3], // emailid
                 (String) obj[4], // phoneNumber
                 dobStr,          // dateOfBirth as String
                 adharStr,        // aadharNumber as String
                 (String) obj[7]  ,
                 (String) obj[8]  // profilePhoto
            );
      }

      public List<BaseDTO> getAllUsersByRole(Long roleId) {
          List<BaseDTO> userDetails = new ArrayList<>();
          log.info("Fetching users with role ID: {}", roleId);
          List<Object[]> newuserDetails = userDetailRepository.findByIsActiveAndStatusAndRoleId(
               Boolean.TRUE, Status.APPROVED.getDisplayName(), roleId);
          for (Object[] obj : newuserDetails) {
               userDetails.add(new BaseDTO(
                    obj[0] != null ? Long.parseLong(obj[0].toString()) : null,
                    obj[1] != null ? obj[1].toString() : null
               ));
          }
          log.info("Found {} users with role ID: {}", userDetails.size(), roleId);
          return userDetails;
     }
}
 
