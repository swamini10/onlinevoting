package com.onlinevoting.controller;

import com.onlinevoting.model.UserDetail;
import com.onlinevoting.service.UserDetailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.dto.StatusUpdateRequestDTO;
import com.onlinevoting.dto.UserDetailDTO;
import com.onlinevoting.enums.Status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping(path = "/v1/user_detail", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse<UserDetail>> createUser(
            @RequestPart("user") @Valid String userDetailStr,
            @RequestPart("photo") byte[] profilePhoto) throws Exception {
        UserDetail detail = new ObjectMapper().readValue(userDetailStr, UserDetail.class);
        detail.setPhoto(profilePhoto);
        UserDetail savedUser = userDetailService.saveUser(detail);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, savedUser, null);
        return ResponseEntity.ok(response);
    }

    // Get API by email id
    @GetMapping(path = "/v1/user_detail/{id}", produces = { "application/json" })
    public ResponseEntity<ApiResponse<UserDetail>> getUserById(@PathVariable Long id) {
        UserDetail userDetail = userDetailService.getUserById(id);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, userDetail, null);
        return ResponseEntity.ok(response);
    }

    // Put API to update user details
    @PutMapping(path = "/v1/user_detail", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse<UserDetail>> updateUser(
            @RequestPart("user") @Valid String userDetail,
            @RequestPart("photo") byte[] profilePhoto) throws Exception {
        UserDetail detail = new ObjectMapper().readValue(userDetail, UserDetail.class);
        detail.setPhoto(profilePhoto);

        UserDetail updatedUser = userDetailService.updateUser(detail);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, updatedUser, null);
        return ResponseEntity.ok(response);
    }

    // SOFT DELETE API to delete user details
    @DeleteMapping(path = "/v1/user_detail/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        userDetailService.deleteUser(id);
        ApiResponse<String> response = new ApiResponse<>(true, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    

    @GetMapping(path = "/v1/user_detail/findbyStatus", produces = { "application/json"})
    public ResponseEntity<ApiResponse<List<UserDetailDTO>>> getAllPendingApprovalUsers(
        @RequestParam String status, @RequestParam String orderBy, @RequestParam String order) {
        List<UserDetailDTO> userDetails = userDetailService.getAllPendingApprovalUsers(status, orderBy, order);
        ApiResponse<List<UserDetailDTO>> response = new ApiResponse<>(true, userDetails, null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/v1/user_detail/approve/{id}")
    public ResponseEntity<ApiResponse<String>> approveUser(@PathVariable Long id, @RequestBody StatusUpdateRequestDTO statusUpdateRequest ) {
        userDetailService.approveUser(id, statusUpdateRequest.getStatus());
        ApiResponse<String> response = new ApiResponse<>(true, "User " + statusUpdateRequest.getStatus().toLowerCase() + " successfully", null);
        return ResponseEntity.ok(response);
    }
    
}