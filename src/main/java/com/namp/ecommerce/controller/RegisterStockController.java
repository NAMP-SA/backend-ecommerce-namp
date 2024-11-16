package com.namp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.namp.ecommerce.dto.RegisterStockDTO;
import com.namp.ecommerce.model.RegisterStock;
import com.namp.ecommerce.service.IRegisterStockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class RegisterStockController {
    @Autowired
    private IRegisterStockService registerStockService;

    @GetMapping("registerStock")
    public ResponseEntity<?> getRegisterStocks(){
        try{
            return ResponseEntity.ok(registerStockService.getRegisterStocks());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error showing the Registers Stocks:" + e.getMessage());
        }
    }

    @PostMapping("registerStock")
    public ResponseEntity<?> createRegisterStock(@Valid @RequestBody RegisterStockDTO registerStockDTO){
        if (registerStockDTO.getQuantity()<0){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Quantity cannot be less than zero");
        }

        try{
            RegisterStockDTO createdRegisterStockDTO = registerStockService.save(registerStockDTO);

            if (createdRegisterStockDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This Register Stock already exists");
            }

            return ResponseEntity.ok(registerStockDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating the Register Stock:" + e.getMessage());
        }

    }

    @DeleteMapping("registerStock/{id}")
    public ResponseEntity<?> deleteRegisterStock(@PathVariable long id){
        try{
            RegisterStockDTO registerStockDTO = registerStockService.findById(id);

            if (registerStockDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("The Register Stock does not exists");
            }
            registerStockService.delete(registerStockDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error deleting the Register Stock:" + e.getMessage());
        }


        
    }
    
    @DeleteMapping("registerStockWO/{id}")
    public ResponseEntity<?> deleteRegisterStocWO(@PathVariable long id){
        try{
            RegisterStockDTO registerStockDTO = registerStockService.findById(id);

            if (registerStockDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("The Register Stock does not exists");
            }
            registerStockService.deleteWOStock(registerStockDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error deleting the Register Stock:" + e.getMessage());
        }


        
    }

    @PutMapping("registerStock/{id}")
    public ResponseEntity<?> updateRegisterStock(@PathVariable long id, @Valid @RequestBody RegisterStock registerStock){
        if (registerStock.getQuantity()<0){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Quantity cannot be less than zero");
        }

        try{
            RegisterStockDTO existingRegisterStockDTO = registerStockService.findById(id);

            if (existingRegisterStockDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("The Register Stock does not exists");
            }

            RegisterStockDTO updatedRegisterStockDTO = registerStockService.update(existingRegisterStockDTO, registerStock);

            if (updatedRegisterStockDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("The entered Register Stock already exists");
            }
            
            return ResponseEntity.ok(updatedRegisterStockDTO);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error updating the Register Stock:" + e.getMessage());
        }
    }

    
}
