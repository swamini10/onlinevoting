package com.onlinevoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City cityId; // 1-1

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State stateId; // 1-1

    private String zipCode; // 1-1

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryId; // 1-1

    private Boolean isActive;

    public Address(Long id) {
        this.id = id;
    }
    
    public Address(String street, City cityId, State stateId, String zipCode, Country countryId, Boolean isActive) {
        this.street = street;
        this.cityId = cityId;
        this.stateId = stateId;
        this.zipCode = zipCode;
        this.countryId = countryId;
        this.isActive = isActive;
    }

    public Address() {

    }

    @PrePersist
    public void prePersist() {
        if (isActive == null) {
            isActive = true; // Default value
        }
    }
}
