package com.onlinevoting.service;

import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.dto.UserDetailDTO;
import com.onlinevoting.enums.Status;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.repository.UserDetailRepository;

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

     public UserDetail saveUser(UserDetail userDetail) {
          var emailId = userDetail.getEmailId();

          UserDetail existingUserDetail = userDetailRepository.findByEmailId(emailId);
          if (existingUserDetail != null) {
               throw new IllegalArgumentException("User with account for email " + emailId + " already exists.");
          }

          UserDetail newUserDetail = new UserDetail(userDetail.getFirstName(), userDetail.getLastName(),
                    userDetail.getMiddleName(), userDetail.getEmailId(), userDetail.getPhoneNo(),
                    userDetail.getAddress(),
                    userDetail.getDob(), userDetail.getAadharNumber(), userDetail.getPhoto());

          newUserDetail.setActive(false);
          newUserDetail.setStatus(Status.PENDING.getDisplayName());

          UserDetail uDetails = userDetailRepository.save(newUserDetail);
          // Send welcome email
          // try {
          //      emailService.sendEmailWithTemplate(userDetail.getEmailId(), EmailConstants.WELCOME_SUBJECT,
          //                EmailConstants.USER_CREATE_TEMPLATE, Map.of("name", userDetail.getFirstName()));
          // } catch (Exception e) {
          //      e.printStackTrace();
          // }
          return uDetails;
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
     public UserDetail getUserByEmail(String email) {
          return userDetailRepository.findByEmailId(email);
     }

     public UserDetail getUserById(Long id) {
          Optional<UserDetail> userDetail = userDetailRepository.findById(id);
          if (userDetail.isEmpty()) {
               throw new IllegalArgumentException("User with account for ID " + id + " does not exist.");
          }
          return userDetail.get();
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
          return new UserDetailDTO((String) obj[0], (String) obj[1], (String) obj[2], String.valueOf(obj[3]), (String) obj[4]);
     }
}
