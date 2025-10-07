package com.onlinevoting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
    
    private String street;
    private Long cityId;
    private Long stateId;
    private String zipCode;
    private Long countryId;
    private Boolean isActive;
}