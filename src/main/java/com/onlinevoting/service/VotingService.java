package com.onlinevoting.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.CandidateResponseDTO;
import com.onlinevoting.dto.VotingDetail;
import com.onlinevoting.model.Candidate;
import com.onlinevoting.model.Election;
import com.onlinevoting.model.Party;
import com.onlinevoting.model.UserDetail;
import com.onlinevoting.model.Voting;
import com.onlinevoting.repository.VotingRepository;

@Service
public class VotingService {

    private final VotingRepository votingRepository;
    private final CandidateService candidateService;

    private final UserDetailService userDetailService;

    private final PartyService partyService;

    public VotingService(VotingRepository votingRepository, CandidateService candidateService, PartyService partyService, UserDetailService userDetailService) {
        this.votingRepository = votingRepository;
        this.candidateService = candidateService;
        this.partyService = partyService;
        this.userDetailService = userDetailService;
    }

    public void saveVoting(Voting voting) {
        votingRepository.save(voting);
    }

    public void createVotingEntries(Long electionId, List<UserDetail> voters, Election election) {
        for (UserDetail voter : voters) {
            Voting voting = new Voting();
            Election election2Election = new Election();
            election2Election.setId(electionId);
            voting.setElection(election2Election);
            voting.setVoter(voter);
            voting.setCandidateId(null); // keep candidateId null initially for uncast votes
            voting.setActive(true);

            // TO GET ELECTION START AND END DATE TIME
            LocalDate electionDate = election.getElectionDate();
            LocalDateTime electionStartDateTime = electionDate.atTime(7, 30); // 7:30 AM
            LocalDateTime electionEndDateTime = electionDate.atTime(17, 30);   // 5:30 PM
            voting.setElectionStartDateTime(electionStartDateTime);
            voting.setElectionEndDateTime(electionEndDateTime);
            votingRepository.save(voting);
        }
    }   

    public VotingDetail getVotingDetail(String emailId) {
        // Fetch voterId using emailId
        List<UserDetail> userDetail = userDetailService.findUsersByEmail(emailId);
        if(userDetail == null || userDetail.isEmpty()) {
            throw new RuntimeException("User not found with email: " + emailId);
        }

        String voterId = userDetail.get(0).getId().toString();
        List<Voting> voting = votingRepository.findByVoterId(voterId);
        if(voting != null && !voting.isEmpty()) {
          Voting votingEntity = voting.get(0);
            // Map Voting entities to VotingDetail DTO
            VotingDetail votingDetail = new VotingDetail();
            // Populate votingDetail fields as needed
            if(votingEntity.getCandidateId() == null ){
                // Vote has not been cast yet
                votingDetail.setElectionId(votingEntity.getElection().getId().toString());
                votingDetail.setElectionName(votingEntity.getElection().getElectionName());
                votingDetail.setElectionStartTime(votingEntity.getElectionStartDateTime());
                votingDetail.setElectionEndTime(votingEntity.getElectionEndDateTime());
                List<Candidate> candidates = candidateService.getCandidateEntityByElectionId(votingEntity.getElection().getId());
                // You might want to set this list to votingDetail if it has a field for candidates
                // votingDetail.setCandidates(candidates);
                if(candidates != null && !candidates.isEmpty()) {
                    List<com.onlinevoting.dto.CandidateInfo> candidateInfos = candidates.stream().map(candidate -> {
                        com.onlinevoting.dto.CandidateInfo info = new com.onlinevoting.dto.CandidateInfo();
                        info.setCandidateId(candidate.getId().toString());
                        info.setCandidateName(candidate.getFullName());
                        
                        Party party = partyService.getPartyById(candidate.getParty().getId());   
                        if(party == null) {
                            throw new RuntimeException("Party not found with id: " + candidate.getParty().getId());
                        }
                        info.setPartyName(party.getName());
                        info.setSymbolUrl(party.getLogoUrl());

                        info.setPhoto(candidate.getCandidatePhoto());
                        return info;
                    }).toList();
                    votingDetail.setCandidates(candidateInfos);
                }

            }else {
                throw new IllegalArgumentException("Vote has already been cast for this voter.");
            }
            return votingDetail;
        }
        throw new IllegalArgumentException("Vote has already been cast for this voter.");  
      }

    public List<Voting> getAllVotingsByElectionId(Long electionId) {
        return votingRepository.findAllByElection(electionId);
    }
}
