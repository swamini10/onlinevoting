package com.onlinevoting.repository;

import org.springframework.stereotype.Repository;

import com.onlinevoting.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
