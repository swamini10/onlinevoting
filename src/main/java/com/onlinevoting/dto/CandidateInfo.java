package com.onlinevoting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateInfo {

    private String candidateId;
    private String candidateName;
    private String partyName;
    private String symbolUrl;
    private String photo;
    
}
