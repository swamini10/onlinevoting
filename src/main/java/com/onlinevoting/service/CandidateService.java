package com.onlinevoting.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinevoting.constants.EmailConstants;
import com.onlinevoting.dto.CandidateResponseDTO;
import com.onlinevoting.dto.CandidateVotingDetail;
import com.onlinevoting.enums.Status;
import com.onlinevoting.model.Candidate;
import com.onlinevoting.repository.CandidateRepository;
import com.onlinevoting.util.UserContextHelper;
                                                        
@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ObjectMapper objectMapper;
    private UserContextHelper userContextHelper;
    private final EmailService emailService;

    public CandidateService(CandidateRepository candidateRepository, UserContextHelper userContextHelper,
         EmailService emailService) {
        this.candidateRepository = candidateRepository;
        this.userContextHelper = userContextHelper;
        this.emailService = emailService;
        this.objectMapper = new ObjectMapper();
        // Configure ObjectMapper to handle LocalDate properly
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }
  
    public Candidate saveCandidate(String candidate) {
       try { 
        Candidate candidateObj = objectMapper.readValue(candidate, Candidate.class);
        candidateObj.setActive(false);
        candidateObj.setStatus(Status.PENDING.getDisplayName());
        candidateObj.setEmailId(userContextHelper.getCurrentUserEmail());
        return candidateRepository.save(candidateObj);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse candidate JSON: " + e.getMessage(), e);
        }
    }
    

    public List<CandidateResponseDTO> getCandidateByElectionId(Long electionId) {
        return candidateRepository.findByElection_Id(electionId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public List<Candidate> getCandidateEntityByElectionId(Long electionId) {
        return candidateRepository.findByElection_Id(electionId);
    }

    public List<CandidateVotingDetail> getCandidateByElectionIdWithDetail(Long electionId) {
        return candidateRepository.findByElection_Id(electionId).stream()
                .map(this::convertCandidateVotingDetail)
                .collect(Collectors.toList());
    }

    public List<CandidateResponseDTO> getCandidatebyStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status parameter is required.");
        }

        return candidateRepository.findByStatus(status).stream()
                .map(this::toDto).toList();
    }
    

    public void updateStatusOfCandidate(Long candidateId, String status, String noteForStatus) {
         Candidate candidate = candidateRepository.findById(candidateId)
                 .orElseThrow(() -> new IllegalArgumentException("Candidate not found with id: " + candidateId));        
         candidate.setStatus(status);
         candidate.setNoteForStatus(noteForStatus);
         candidateRepository.save(candidate);
         
         // Send email notification
         sendStatusUpdateEmail(candidate, status, noteForStatus);
     }

    public List<CandidateResponseDTO> getCandidatesByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status parameter is required.");
        }       
        return candidateRepository.findByStatus(status).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private CandidateResponseDTO toDto(Candidate candidate) {
        String partyName = candidate.getParty().getId() != null ? candidate.getParty().getName().toString() : null;
        String electionName = candidate.getElection() != null ? candidate.getElection().getElectionName() : null;
        return new CandidateResponseDTO(
               candidate.getId(),
                candidate.getFirstName(),
                candidate.getMiddleName(),
                candidate.getLastName(),
                candidate.getOccupation(),
                candidate.getIncome(),
                partyName,
                electionName,
                candidate.getStatus(),
                candidate.getEmailId(),
                candidate.getNoteForStatus(),
                candidate.getDob() != null ? candidate.getDob().toString() : null,
                candidate.getParty().getLogoUrl(),
                candidate.getCandidatePhoto(),
                candidate.getIncomeProof()
        );
    }

  private CandidateVotingDetail convertCandidateVotingDetail(Candidate candidate) {
        String partyName = candidate.getParty().getId() != null ? candidate.getParty().getName().toString() : null;
        return new CandidateVotingDetail(String.join(" ", candidate.getFirstName(), 
        candidate.getMiddleName(), candidate.getLastName()) 
        , partyName, candidate.getParty().getLogoUrl());
    }

    private void sendStatusUpdateEmail(Candidate candidate, String status, String noteForStatus) {
        try {
            Map<String, Object> templateData = createEmailTemplateData(candidate, status, noteForStatus);
            
            emailService.sendEmailWithTemplate(
                candidate.getEmailId(),
                EmailConstants.CANDIDATE_STATUS_UPDATE_SUBJECT + " - " + status,
                EmailConstants.CANDIDATE_STATUS_UPDATE_TEMPLATE,
                templateData
            );
        } catch (Exception e) {
            // Log error but don't fail the status update
            System.err.println("Failed to send status update email to " + candidate.getEmailId() + ": " + e.getMessage());
        }
    }

    private Map<String, Object> createEmailTemplateData(Candidate candidate, String status, String noteForStatus) {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("candidateFirstName", candidate.getFirstName());
        templateData.put("candidateMiddleName", candidate.getMiddleName());
        templateData.put("candidateLastName", candidate.getLastName());
        templateData.put("candidateEmail", candidate.getEmailId());
        templateData.put("status", status);
        templateData.put("noteForStatus", noteForStatus);
        
        // Format dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        templateData.put("applicationDate", candidate.getCreatedDate() != null ? 
            candidate.getCreatedDate().format(formatter) : "N/A");
        templateData.put("statusUpdateDate", LocalDateTime.now().format(formatter));
        
        // Add party and election info if available
        if (candidate.getParty() != null && candidate.getParty().getName() != null) {
            templateData.put("partyName", candidate.getParty().getName());
        }
        if (candidate.getElection() != null) {
            templateData.put("electionName", candidate.getElection().getElectionName());
        }
        
        // Add login URL (you may need to configure this based on your application properties)
        templateData.put("loginUrl", "/login");
        
        return templateData;
    }
}

