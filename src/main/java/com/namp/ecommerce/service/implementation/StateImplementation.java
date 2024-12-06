package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.StateDTO;
import com.namp.ecommerce.dto.StateWithOrdersDTO;
import com.namp.ecommerce.mapper.MapperState;
import com.namp.ecommerce.model.State;
import com.namp.ecommerce.repository.IStateDAO;
import com.namp.ecommerce.service.IStateService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StateImplementation implements IStateService {

    @Autowired
    private IStateDAO stateDAO; 

    @Autowired
    private MapperState mapperState; 

    @Override
    public List<StateDTO> getStates() {
       return stateDAO.findAll()
                .stream()
                .map(mapperState::convertStateToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StateWithOrdersDTO> getStatesWithOrders() {
        return stateDAO.findAll()
            .stream()
            .map(mapperState::convertStateWithOrderToDTO)
            .collect(Collectors.toList());

    }

    @Override
    public StateDTO save(StateDTO stateDTO) {
        //Normalizar los espacios en blanco y convertir a mayusculas 
        String normalizedName = stateDTO.getName().replaceAll("\\s+", " ").trim().toUpperCase();
        
        if(!verifyName(normalizedName)){
            stateDTO.setName(normalizedName);

            State state = mapperState.convertDtoToState(stateDTO);
            State savedState = stateDAO.save(state); 
            return mapperState.convertStateToDTO(savedState); 

        }
        return null; 
    }

    @Override
    public StateDTO update(StateDTO existingStateDTO, State state) {
        //Buscar el Estado existente 
        State existingSate = stateDAO.findByIdState(existingStateDTO.getIdState()); 
        if (existingSate == null){
            return null; 
        }

        // Normalizar los espacios en blanco y convertir a mayúsculas
        String normalizedName = state.getName().replaceAll("\s+", " ").trim().toUpperCase(); 

        //Verifica que el nombre esta disponible
        if(verifyName(normalizedName, existingStateDTO.getIdState())){
            return null; 
        }

        //Actualizar los campos de la entidad existente
        existingSate.setName(normalizedName);
        
        //Guardar el estado actualizado 
        State updatedState = stateDAO.save(existingSate); 

        //Devolvemos el DTO del estado actualizado
        return mapperState.convertStateToDTO(updatedState); 

    }

    @Override
    public void delete(StateDTO stateDTO) {
        State state = stateDAO.findByIdState(stateDTO.getIdState());
        if (state == null) {
            throw new EntityNotFoundException("State not found with ID: " + stateDTO.getIdState());
        }
        stateDAO.delete(state);
    }

    @Override
    public StateDTO findbyid(long id) {
        State state = stateDAO.findByIdState(id);
        if (state == null) {
            return null;
        }
        return mapperState.convertStateToDTO(state);
    }

    @Override
    public boolean verifyName(String normalizedName) {
        List<State> states = stateDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        //Comparar el nombre del estado que se quiere guardar, con todos los demas sin espacio para ver si es el mismo
        for(State state : states){
            if(name.equals(state.getName().replaceAll("\\s+", ""))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyName(String normalizedName, long stateId) {
        List<State> states = stateDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        //Verifica si se repite el nombre en los demas estados, menos con la que se está actualizando
        for (State state : states) {
            if (state.getIdState() != stateId && name.equals(state.getName().replaceAll("\s+", ""))) {
                return true;
            }
        }

        return false;
    }


    
}
