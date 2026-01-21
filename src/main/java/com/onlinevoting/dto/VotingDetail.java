package com.onlinevoting.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotingDetail {

    private String electionId;
    private String electionName;
    private LocalDateTime electionStartTime;
    private LocalDateTime electionEndTime;
    private List<CandidateInfo> candidates;
    
}
