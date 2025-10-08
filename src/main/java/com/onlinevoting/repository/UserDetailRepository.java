package com.onlinevoting.repository;

import com.onlinevoting.model.UserDetail;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByEmailId(String emailId);

    Optional<UserDetail> findById(Long id);
   
    Optional<UserDetail> findByIdAndIsActiveTrue(Long id);

    List<UserDetail> findByIsActiveFalse();

    @Query("SELECT userDetail.firstName, userDetail.lastName, userDetail.emailId, userDetail.id, " +
    "userDetail.status FROM UserDetail userDetail WHERE userDetail.isActive = :isActive AND userDetail.status = :status")
    List<Object[]> findByIsActiveAndStatus(Boolean isActive, String status);
}


