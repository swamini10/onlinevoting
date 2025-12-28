package com.onlinevoting.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.dto.ElectionResponseDto;
import com.onlinevoting.enums.Status;
import com.onlinevoting.model.Election;
import com.onlinevoting.repository.ElectionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ObjectMapper objectMapper;
    private final CountryService countryService;
    private final StateService stateService;
    private final CityService cityService;
    private final UserDetailService userDetailService;

    public ElectionService(ElectionRepository electionRepository, CountryService countryService, 
        StateService stateService, CityService cityService, UserDetailService userDetailService) {
        this.electionRepository = electionRepository;
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.userDetailService = userDetailService;
        this.objectMapper = new ObjectMapper();
        // Configure ObjectMapper to handle LocalDate properly
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    public Election saveElection(String election) {
        try {
            // Convert JSON string to Election object
            Election electionObject = objectMapper.readValue(election, Election.class);
            electionObject.setActive(true);
            electionObject.setStatus(Status.PENDING.getDisplayName());
            if(electionObject.getElectionDate().isAfter(electionObject.getResultDate())) {
                throw new IllegalArgumentException("Election date must be before result date.");
            }
            if(electionObject.getFormEndDate().isAfter(electionObject.getElectionDate())) {
                throw new IllegalArgumentException("Form end date must be before election date.");
            }
            // Save the election object to database
            return electionRepository.save(electionObject);
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse election JSON: " + e.getMessage(), e);
        }
    } 

    public List<ElectionResponseDto> getAllElections() {
        return electionRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }


    public void approveElection(Long electionId, String status) {
        Election election = electionRepository.findById(electionId)
            .orElseThrow(() -> new IllegalArgumentException("Election not found with id: " + electionId));

        election.setStatus(status);
        electionRepository.save(election);
    }

    public List<ElectionResponseDto> getElectionsByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status parameter is required.");
        }


        return electionRepository.findByStatus(status).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public List<BaseDTO> getApprovedElections() {
        List<Election> approvedElections = electionRepository.findByStatusAndIsActiveTrue(Status.APPROVED.getDisplayName());

      return approvedElections.stream()
            .map(election -> new BaseDTO(election.getElectionId(), election.getElectionName()))
            .toList();
    }

    private ElectionResponseDto toDto(Election election) {
        String countryName = countryService.getById(election.getCountry().getId()).getName();
        String stateName = stateService.getById(election.getState().getId()).getName();
        String cityName = cityService.getById( election.getCity().getId()).getName();
        String officerName = userDetailService.getUserById(election.getOfficer().getId()).getFullName();
        return new ElectionResponseDto(
            election.getElectionId(),
            election.getElectionName(),
            election.getElectionDate(),
            election.getResultDate(),
            countryName,
            stateName,
             cityName,
            officerName,
            election.getStatus()
        );
    }
}