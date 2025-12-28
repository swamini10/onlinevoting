package com.onlinevoting.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.onlinevoting.model.Country;

public interface CountryRepository extends ListCrudRepository<Country, Long> {
    public List<Country> findAllByIsActiveTrue();
    public java.util.Optional<Country> findByIdAndIsActiveTrue(Long id);
}
