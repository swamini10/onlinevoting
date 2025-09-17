package com.onlinevoting.controller;

import com.onlinevoting.model.UserDetail;
import com.onlinevoting.service.UserDetailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinevoting.dto.ApiResponse;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping(path = "/v1/user_detail", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<UserDetail>> createUser(
        @RequestPart("user")  @Valid String userDetail,
        @RequestPart("photo") byte[] profilePhoto) throws Exception {
        UserDetail detail =  new ObjectMapper().readValue(userDetail, UserDetail.class);
        detail.setPhoto(profilePhoto);
        UserDetail savedUser = userDetailService.saveUser(detail);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, savedUser, null);
        return ResponseEntity.ok(response);
    }

    // Get API by email id
    @GetMapping(path = "/v1/user_detail", produces = {"application/json"})
    public ResponseEntity<ApiResponse<UserDetail>> getUserByEmail(HttpServletRequest request) {
        UserDetail userDetail = userDetailService.getUserByEmail(request.getHeader("email"));
        ApiResponse<UserDetail> response = new ApiResponse<>(true, userDetail, null);
        return ResponseEntity.ok(response);
    }

    // Put API to update user details
    @PutMapping(path = "/v1/user_detail", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<UserDetail>> updateUser(
        @RequestPart("user")  @Valid String userDetail,
        @RequestPart("photo") byte[] profilePhoto) throws Exception {
        UserDetail detail =  new ObjectMapper().readValue(userDetail, UserDetail.class);
        detail.setPhoto(profilePhoto);

        UserDetail updatedUser = userDetailService.updateUser(detail);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, updatedUser, null);
        return ResponseEntity.ok(response);
        }
}
