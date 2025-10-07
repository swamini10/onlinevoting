package com.onlinevoting.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import com.onlinevoting.model.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends ListCrudRepository<City, Long> {
    List<City> findByStateId(Long stateId);
}
