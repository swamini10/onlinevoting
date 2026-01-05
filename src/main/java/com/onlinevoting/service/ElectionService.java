package com.onlinevoting.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.dto.ElectionAddressDTO;
import com.onlinevoting.dto.ElectionResponseDto;
import com.onlinevoting.dto.StatusUpdateRequestDTO;
import com.onlinevoting.enums.Status;
import com.onlinevoting.model.Election;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.repository.ElectionRepository;
import com.onlinevoting.repository.UserDetailRepository;
import com.onlinevoting.constants.EmailConstants;

import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
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
    private final EmailService emailService;
    private final UserDetailRepository userDetailRepository;

    public ElectionService(ElectionRepository electionRepository, CountryService countryService, 
        StateService stateService, CityService cityService, UserDetailService userDetailService, 
        EmailService emailService, UserDetailRepository userDetailRepository) {
        this.electionRepository = electionRepository;
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.userDetailService = userDetailService;
        this.emailService = emailService;
        this.userDetailRepository = userDetailRepository;
        this.objectMapper = new ObjectMapper();
        // Configure ObjectMapper to handle LocalDate properly
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    public void publishElection(Long electionId, StatusUpdateRequestDTO statusUpdateRequest) {
        Election election = electionRepository.findById(electionId)
            .orElseThrow(() -> new IllegalArgumentException("Election not found with id: " + electionId));

        election.setNote(statusUpdateRequest.getNote());
        election.setIsPublish(statusUpdateRequest.getIsPublish());
        // Send email notification logic can be added here
       List<UserDetail> activeVoters = userDetailRepository.findActiveVoters();
       List<String> voterEmails = activeVoters.stream()
            .map(UserDetail::getEmailId).toList();

            if (statusUpdateRequest.getIsPublish() != null && statusUpdateRequest.getIsPublish()) {
            // Prepare email content
            Map<String, Object> emailModel = new HashMap<>();
            emailModel.put("electionName", election.getElectionName());
            emailModel.put("electionDate", election.getElectionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            emailModel.put("resultDate", election.getResultDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            emailModel.put("note", statusUpdateRequest.getNote());

            // Send email to all active voters in the election's city
            try{
                emailService.sendEmailWithTemplate(voterEmails, EmailConstants.ELECTION_PUBLISHED_SUBJECT, 
               EmailConstants.ELECTION_PUBLISHED_TEMPLATE, emailModel);
            }catch(Exception e){
                System.out.println("Error sending election published emails: " + e.getMessage());
            }

        }
       electionRepository.save(election);
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

    public ElectionAddressDTO getElectionById(Long electionId) {
        Election election = electionRepository.findById(electionId)
            .orElseThrow(() -> new IllegalArgumentException("Election not found with id: " + electionId));
        return toDtoWithIdsDto(election);
    }

        public ElectionResponseDto getElectionDetails(Long electionId) {
        Election election = electionRepository.findById(electionId)
            .orElseThrow(() -> new IllegalArgumentException("Election not found with id: " + electionId));
        return toDto(election);
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

      return approvedElections.stream().filter(e->!e.getResultDate().isBefore(LocalDate.now()))
            .map(election -> new BaseDTO(election.getId(), election.getElectionName()))
            .toList();
    }

 private ElectionAddressDTO toDtoWithIdsDto(Election election) {
        return new ElectionAddressDTO(
            election.getCountry().getId(),
            election.getState().getId(),
             election.getCity().getId()
        );
    }
    private ElectionResponseDto toDto(Election election) {
        String countryName = countryService.getById(election.getCountry().getId()).getName();
        String stateName = stateService.getById(election.getState().getId()).getName();
        String cityName = cityService.getById( election.getCity().getId()).getName();
        String officerName = userDetailService.getUserById(election.getOfficer().getId()).getFullName();
        return new ElectionResponseDto(
            election.getId(),
            election.getElectionName(),
            election.getElectionDate(),
            election.getResultDate(),
            countryName,
            stateName,
             cityName,
            officerName,
            election.getStatus(),
            election.getIsPublish()
        );
    }
    
    /**
     * Sends email notifications to eligible voters when an election is published
     */
    private void sendElectionPublishedNotification(Election election) {
        try {
            // Get all active voters in the election's city
            List<UserDetail> eligibleVoters = userDetailRepository.findActiveVoters();
            
            // Create email template data
            Map<String, Object> templateData = createElectionEmailTemplateData(election);
            
            // Send email to each eligible voter
            for (UserDetail voter : eligibleVoters) {
                try {
                    // Add personalized data for each voter
                    templateData.put("voterName", voter.getFirstName());
                    templateData.put("voterEmail", voter.getEmailId());
                    
                    emailService.sendEmailWithTemplate(
                        voter.getEmailId(),
                        EmailConstants.ELECTION_PUBLISHED_SUBJECT,
                        EmailConstants.ELECTION_PUBLISHED_TEMPLATE,
                        templateData
                    );
                    
                } catch (Exception e) {
                    // Log individual email failures but continue with others
                    System.err.println("Failed to send election notification email to " + voter.getEmailId() + ": " + e.getMessage());
                }
            }
            
            System.out.println("Election notification emails sent to " + eligibleVoters.size() + " eligible voters");
            
        } catch (Exception e) {
            // Log error but don't fail the election publication
            System.err.println("Failed to send election notification emails: " + e.getMessage());
        }
    }
    
    /**
     * Creates template data for election notification emails
     */
    private Map<String, Object> createElectionEmailTemplateData(Election election) {
        Map<String, Object> templateData = new HashMap<>();
        
        // Election details
        templateData.put("electionName", election.getElectionName());
        templateData.put("electionDate", election.getElectionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        templateData.put("formEndDate", election.getFormEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        templateData.put("resultDate", election.getResultDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        // Location details
        String countryName = countryService.getById(election.getCountry().getId()).getName();
        String stateName = stateService.getById(election.getState().getId()).getName();
        String cityName = cityService.getById(election.getCity().getId()).getName();
        
        templateData.put("country", countryName);
        templateData.put("state", stateName);
        templateData.put("city", cityName);
        
        // Officer details
        String officerName = userDetailService.getUserById(election.getOfficer().getId()).getFullName();
        templateData.put("officerName", officerName);
        
        // Additional information
        templateData.put("status", election.getStatus());
        templateData.put("note", election.getNote());
        templateData.put("publishDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        return templateData;
    }
}