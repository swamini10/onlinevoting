package com.onlinevoting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinevoting.model.UserOtpDetails;

public interface UserOtpDetailsRepository extends JpaRepository<UserOtpDetails, Long> {
      Optional<UserOtpDetails> findByUserDetailIdAndIsOtpUsedFalseAndIsActiveTrue(Long userId);
}
