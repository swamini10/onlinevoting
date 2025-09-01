package com.onlinevoting.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.repository.UserDetailRepository;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private EmailService emailService;

    public UserDetail saveUser(UserDetail userDetail) {
        UserDetail userDetail1 = new UserDetail(userDetail.getFirstName(),userDetail.getLastName(),
                userDetail.getMiddleName(),userDetail.getEmailId(),userDetail.getPhoneNo(),userDetail.getAddress(),
                userDetail.getDob(),userDetail.getAadharNumber());
       UserDetail uDetails =  userDetailRepository.save(userDetail1);
         try {
              emailService.sendEmailWithTemplate(userDetail.getEmailId(), EmailConstants.WELCOME_SUBJECT,
                     EmailConstants.USER_CREATE_TEMPLATE, Map.of("name", userDetail.getFirstName()));
         } catch (Exception e) {
              e.printStackTrace();
         }  
         return uDetails;
    }
}