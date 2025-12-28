package com.onlinevoting.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.model.State;
import com.onlinevoting.repository.StateRepository;

@Service
public class StateService {

    private final StateRepository  stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<BaseDTO> getStatesByCountryId(Long countryId) {
        List<State> list = stateRepository.findByCountryId(countryId);
        List<BaseDTO> dtoList = new ArrayList<>();

        for (State state : list) {
            BaseDTO dto = new BaseDTO(state.getId(),state.getName());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public State getById(Long id) {
        return stateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("State not found with id: " + id));
    }
}
