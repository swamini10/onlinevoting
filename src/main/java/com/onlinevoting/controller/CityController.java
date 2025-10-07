package com.onlinevoting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinevoting.dto.ApiResponse;
import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.service.CityService;

@RestController
@RequestMapping("/v1/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(path = "/by-state/{stateId}", produces = { "application/json" })
    public ResponseEntity<ApiResponse<List<BaseDTO>>> getCitiesByStateId(@PathVariable Long stateId) {
        List<BaseDTO> cities = cityService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(new ApiResponse<>(true, cities, null));
    }
}