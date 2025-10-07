package com.onlinevoting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.model.Country;
import com.onlinevoting.repository.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
  
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll() {        
        return countryRepository.findAllByIsActiveTrue();
    }
    
}
