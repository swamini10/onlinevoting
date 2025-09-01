package com.onlinevoting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.service.UserDetailService;

import jakarta.validation.Valid;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping("/v1/user_detail")
    public ResponseEntity<ApiResponse<UserDetail>> createUser(@Valid @RequestBody UserDetail userDetail) {
        UserDetail savedUser = userDetailService.saveUser(userDetail);
        ApiResponse<UserDetail> response = new ApiResponse<>(true, savedUser, null);
        return ResponseEntity.ok(response);
    }
}