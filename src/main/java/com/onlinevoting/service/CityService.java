package com.onlinevoting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.model.City;
import com.onlinevoting.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    

    public List<BaseDTO> getCitiesByStateId(Long stateId) {
        List<City> cities = cityRepository.findByStateId(stateId);

        return cities.stream()
                .map(city -> new BaseDTO(city.getId(),city.getName()))
                .toList();
    }

    public City getById(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + id));
    }
}
