package com.onlinevoting.repository;

import com.onlinevoting.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByEmailId(String emailId);
}
