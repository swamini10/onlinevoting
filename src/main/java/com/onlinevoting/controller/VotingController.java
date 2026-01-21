package com.onlinevoting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.model.Voting;
import com.onlinevoting.service.TokenService;
import com.onlinevoting.service.VotingService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class VotingController {

    private final VotingService votingService;
    private final TokenService tokenService;

    public VotingController(VotingService votingService, TokenService tokenService) {
        this.votingService = votingService;
        this.tokenService = tokenService;
    }

    @GetMapping(path = "/v1/voting/", produces = "application/json")
    public ResponseEntity<ApiResponse> getVotingListForVote(HttpServletRequest request) {
          String emailId = tokenService.extractEmailId(request);
        return ResponseEntity.ok(new ApiResponse(true, votingService.getVotingDetail(emailId), null));
    }
}
