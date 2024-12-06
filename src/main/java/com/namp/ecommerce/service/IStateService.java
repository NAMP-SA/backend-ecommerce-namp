package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.StateDTO;
import com.namp.ecommerce.dto.StateWithOrdersDTO;
import com.namp.ecommerce.model.State;

public interface IStateService {
    List<StateDTO> getStates();
    List<StateWithOrdersDTO> getStatesWithOrders();
    //StateithOrdesDTO getStatesIdWithOrders(long id); 
    StateDTO save(StateDTO stateDTO); 
    StateDTO update(StateDTO existingState, State state); 
    void delete(StateDTO stateDTO);
    StateDTO findbyid(long id); 
    boolean verifyName(String normalizedName); 
    boolean verifyName(String normalizedName, long StateId); 
}
