package com.onlinevoting.service;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.AddressRequestDTO;
import com.onlinevoting.model.Address;
import com.onlinevoting.model.City;
import com.onlinevoting.model.Country;
import com.onlinevoting.model.State;
import com.onlinevoting.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

   public Long saveAddress(AddressRequestDTO inputAddress) {
    Address address2 = new Address();
    address2.setStreet(inputAddress.getStreet());
    address2.setZipCode(inputAddress.getZipCode());

    // Set the related entities
    City city = new City();
    city.setId(inputAddress.getCityId());
    address2.setCityId(city);
    
    // Same for state and country...
    State state = new State();
    state.setId(inputAddress.getStateId());
    address2.setStateId(state);

    Country country = new Country();
    country.setId(inputAddress.getCountryId());
    address2.setCountryId(country);

    return addressRepository.save(address2).getId();
   }    
}
