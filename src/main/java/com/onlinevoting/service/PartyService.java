package com.onlinevoting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevoting.dto.BaseDTO;
import com.onlinevoting.model.Party;
import com.onlinevoting.repository.PartyRepository;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public Party save(Party party) {
        party.setActive(true);
        return partyRepository.save(party);
    }

    public List<BaseDTO> getAllParties() {
         // GET ALL PARTIES  
        List<Party> parties = partyRepository.findByIsActiveTrue();

        //  CONVERT TO BaseDTO
          List<BaseDTO> partyDtos = parties.stream().map(party -> new BaseDTO(
              party.getId(),
              party.getName()
          )).toList();

          return partyDtos;
    }
}
