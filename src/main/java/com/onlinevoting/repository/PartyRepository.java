package com.onlinevoting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinevoting.model.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
   public List<Party> findByIsActiveTrue();
}
