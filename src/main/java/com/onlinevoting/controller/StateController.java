package com.onlinevoting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.service.StateService;

@RestController
@RequestMapping("/v1/states")    
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }
    
    @GetMapping(path = "/by-country/{countryId}", produces = { "application/json" })
    public ResponseEntity<ApiResponse<List<BaseDTO>>> getStatesByCountryId(@PathVariable Long countryId) {
        return ResponseEntity.ok(new ApiResponse<>(true, stateService.getStatesByCountryId(countryId), null));
    }

}
