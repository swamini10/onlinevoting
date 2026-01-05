package com.onlinevoting.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Apply to the whole class
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
    private Boolean isPublish;
    
    public ElectionResponseDto(Long electionId, String electionName, String status) {
        this.electionId = electionId;
        this.electionName = electionName;
        this.status = status;
    }
    
    public ElectionResponseDto(String coutry, String state, String city) {
        this.country = coutry;
        this.state = state;
        this.city = city;
    }
}
