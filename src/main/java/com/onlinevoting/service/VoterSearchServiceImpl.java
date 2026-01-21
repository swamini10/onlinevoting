package com.onlinevoting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.UserDetailDTO;
import com.onlinevoting.model.UserDetail;

@Service
public class VoterSearchServiceImpl implements VoterSearchService {

    private final UserDetailService userService;
    public VoterSearchServiceImpl(UserDetailService userService) {
        this.userService = userService;
    }
    
    @Override
    public List<UserDetailDTO> searchVoters(String searchCriteria, String searchValue) {
        List<UserDetailDTO> result = new java.util.ArrayList<>();
        List<UserDetail> users = new java.util.ArrayList<>();
        // Implementation logic to search voters based on criteria and value
        if(searchCriteria.equalsIgnoreCase("phone")) {
            users = userService.findUsersByPhone(searchValue);
           
                        
        } else if(searchCriteria.equalsIgnoreCase("email")) {
            users = userService.findUsersByEmail(searchValue);
        }
         result =  users.stream()
                        .map(user -> convertToDTO(user))
                        .toList();
        return result;
    }

    public UserDetailDTO convertToDTO(UserDetail userDetail) {
        return new UserDetailDTO(
            userDetail.getId()+"",
            userDetail.getFirstName(),
            userDetail.getLastName(),
            userDetail.getEmailId(),
            userDetail.getPhoneNo()+"",
            userDetail.getDob()+"",
            userDetail.getAadharNumber()+"",
            userDetail.getStatus(),
            userDetail.getDocsUrl()
        );
    }

}
