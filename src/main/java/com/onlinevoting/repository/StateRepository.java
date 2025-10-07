package com.onlinevoting.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.onlinevoting.model.State;


public interface StateRepository extends ListCrudRepository<State, Long>{
    List<State> findByCountryId(Long countryId);
}
