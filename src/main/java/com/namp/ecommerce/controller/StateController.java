package com.namp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.namp.ecommerce.dto.StateDTO;
import com.namp.ecommerce.model.State;
import com.namp.ecommerce.service.IStateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class StateController {
    
    @Autowired
    private IStateService stateService; 

    @GetMapping("state")
    public ResponseEntity<?> getStates(){
        try{
            return ResponseEntity.ok(stateService.getStates());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the states:"+e.getMessage());
        }
    }

    @GetMapping("stateWithOrders")
    public ResponseEntity<?> getStatesWithOrders(){
        try{
            return ResponseEntity.ok(stateService.getStatesWithOrders());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the States:" +e.getMessage());
        }
    } 

    @PostMapping("state")
    public ResponseEntity<?> createState(@Valid @RequestBody StateDTO stateDTO) {
        try{
            StateDTO createdStateDTO = stateService.save(stateDTO);

            if (createdStateDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This state already exists");
            }

            return ResponseEntity.ok(createdStateDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the state:"+e.getMessage());
        }
    }

    @DeleteMapping("state/{id}")
    public ResponseEntity<?> deleteState(@PathVariable long id){
        try{
            StateDTO stateDTO = stateService.findbyid(id);

            if (stateDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The state does not exist");
            }

            stateService.delete(stateDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the state: " + e.getMessage());
        }
    }

    @PutMapping("state/{id}")
    public ResponseEntity<?> updateState(@PathVariable long id, @Valid @RequestBody State state){
        try{
            StateDTO existingStateDTO = stateService.findbyid(id);

            if (existingStateDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The state does not exist");
            }

            StateDTO updatedStateDTO = stateService.update(existingStateDTO,state);

            if (updatedStateDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entered name already exists");
            }

            return ResponseEntity.ok(updatedStateDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the state: " + e.getMessage());
        }
    }


}


