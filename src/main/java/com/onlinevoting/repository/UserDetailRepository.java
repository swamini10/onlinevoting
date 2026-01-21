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

    @Query("SELECT userDetail.id, userDetail.firstName, userDetail.lastName, userDetail.emailId, userDetail.phoneNo, userDetail.dob, userDetail.aadharNumber, userDetail.status, userDetail.docsUrl FROM UserDetail userDetail WHERE userDetail.isActive = :isActive AND userDetail.status = :status")
    List<Object[]> findByIsActiveAndStatus(Boolean isActive, String status);

    @Query("SELECT userDetail.id, CONCAT(userDetail.firstName, ' ', userDetail.lastName) FROM UserDetail userDetail WHERE userDetail.isActive = :isActive AND userDetail.status = :status AND userDetail.role.id = :roleId")
    List<Object[]> findByIsActiveAndStatusAndRoleId(Boolean isActive, String status, Long roleId);
    
    @Query("SELECT u FROM UserDetail u WHERE u.phoneNo = :phoneNo")
    List<UserDetail> findByPhoneNo(String phoneNo);

    @Query("SELECT u FROM UserDetail u WHERE u.emailId = :emailId")
    List<UserDetail> findByEmail(String emailId);
    
    @Query("SELECT u FROM UserDetail u WHERE u.isActive = true AND u.status = 'Approved' AND u.role.id = 3 AND u.address.cityId.id = :cityId")
    List<UserDetail> findActiveVoters(Long cityId);
}


