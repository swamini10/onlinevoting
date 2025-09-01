package com.onlinevoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinevoting.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByEmailId(String emailId);
}