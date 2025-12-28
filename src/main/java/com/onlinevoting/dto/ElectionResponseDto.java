package com.onlinevoting.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResponseDto {
    private Long electionId;
    private String electionName;
    private LocalDate electionDate;
    private LocalDate resultDate;
    private String country;
    private String state;
    private String city;
    private String officer;
    private String status;
    
    public ElectionResponseDto(Long electionId, String electionName, String status) {
        this.electionId = electionId;
        this.electionName = electionName;
        this.status = status;
    }
}
