package com.onlinevoting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinevoting.model.Election;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    public List<Election> findByStatus(String status);

    public List<Election> findByStatusAndIsActiveTrue(String status);
}
