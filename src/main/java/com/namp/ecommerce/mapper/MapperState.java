package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.StateDTO;
import com.namp.ecommerce.dto.StateWithOrdersDTO;
import com.namp.ecommerce.model.State;


@Component
public class MapperState {
    
    @Autowired
    private MapperUtil mapperUtil;

    //Metodo para mapperar de SateDTO a State

    public State convertDtoToState(StateDTO stateDTO){
        State state = new State();        
        
        state.setName(stateDTO.getName());

        return state;
        
    } 

    /*
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
    */

    public StateDTO convertStateToDTO(State state){
        StateDTO stateDTO = mapperUtil.convertStateToDTO(state);
        return stateDTO; 
    }

    public StateWithOrdersDTO convertStateWithOrderToDTO(State state){
        StateWithOrdersDTO stateWithOrdersDTO = mapperUtil.convertStateWithOrderToDTO(state);
        return stateWithOrdersDTO; 
    }

}
