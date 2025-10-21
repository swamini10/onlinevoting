package com.onlinevoting.repository;

import com.onlinevoting.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    
    List<Feature> findByIdInAndIsActive(List<Long> ids, Boolean isActive);
}
