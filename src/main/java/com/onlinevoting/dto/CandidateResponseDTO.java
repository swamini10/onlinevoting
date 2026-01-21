package com.onlinevoting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {
    private Long Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String occupation;
    private String income;
    private String party;
    private String election;
    private String status;
    private String emailId;
    private String noteForStatus;
    private String dob;
    private String logo;
    private String candidatePhoto;
    private String incomeProof;
    

    public String getCandidateName() {
        StringBuilder name = new StringBuilder();
        if (firstName != null) {
            name.append(firstName).append(" ");
        }
        if (middleName != null) {
            name.append(middleName).append(" ");
        }
        if (lastName != null) {
            name.append(lastName);
        }
        return name.toString().trim();
    }



}
