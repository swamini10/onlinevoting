package com.onlinevoting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.dto.ElectionResponseDto;
import com.onlinevoting.dto.StatusUpdateRequestDTO;
import com.onlinevoting.service.ElectionService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }
    
    @PostMapping(path="/v1/election", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<Object>> createElection(@RequestBody @Valid String election) {
        electionService.saveElection(election);
        ApiResponse<Object> response = new ApiResponse<>(true, "Election created successfully", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path="/v1/election", produces = "application/json")
    public ResponseEntity<ApiResponse<List<ElectionResponseDto>>> getAllElections() {
        List<ElectionResponseDto> elections = electionService.getAllElections();
        ApiResponse<List<ElectionResponseDto>> response = new ApiResponse<>(true, elections, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/v1/election/findbystatus", produces = { "application/json"})
    public ResponseEntity<ApiResponse<List<ElectionResponseDto>>> getElectionsByStatus(
        @RequestParam String status) {
        List<ElectionResponseDto> elections = electionService.getElectionsByStatus(status);
        ApiResponse<List<ElectionResponseDto>> response = new ApiResponse<>(true, elections, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/v1/election/approved", produces = { "application/json"})
    public ResponseEntity<ApiResponse<List<BaseDTO>>> getApprovedElections() {
        List<BaseDTO> elections = electionService.getApprovedElections();
        ApiResponse<List<BaseDTO>> response = new ApiResponse<>(true, elections, null);
        return ResponseEntity.ok(response);
    }


    @PatchMapping(path = "/v1/election/status/{electionId}", consumes = { "application/json" }, produces = { "application/json" }  )
    public ResponseEntity<ApiResponse<String>> approveUser(@PathVariable Long electionId, 
        @RequestBody StatusUpdateRequestDTO statusUpdateRequest ) {
        electionService.approveElection(electionId, statusUpdateRequest.getStatus());
        ApiResponse<String> response = new ApiResponse<>(true, "Election " + statusUpdateRequest.getStatus().toLowerCase() + " successfully", null);
        return ResponseEntity.ok(response);
    }
    
    
}
 