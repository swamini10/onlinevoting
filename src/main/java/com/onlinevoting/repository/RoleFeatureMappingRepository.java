package com.onlinevoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinevoting.model.RoleFeatureMapping;

import java.util.List;

@Repository
public interface RoleFeatureMappingRepository extends JpaRepository<RoleFeatureMapping, Long> {
    
    List<RoleFeatureMapping> findByRoleIdAndIsActive(Long roleId, Boolean isActive);
}
