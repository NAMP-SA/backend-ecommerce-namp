package com.namp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namp.ecommerce.dto.DiscountTypeDTO;
import com.namp.ecommerce.model.DiscountType;
import com.namp.ecommerce.service.IDiscountTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp") 
public class DiscountTypeController {

    @Autowired
    private IDiscountTypeService discountTypeService; 
    
    @GetMapping("discountType")
    public ResponseEntity<?> getDiscountTypes(){
        try{
            return ResponseEntity.ok(discountTypeService.getDiscountTypes());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the DiscountTypes:"+e.getMessage()); 
        }
    }

    @GetMapping("discountTypeWithPromotions")
    public ResponseEntity<?> getDiscountTypesWithPromotions(){
        try{
            return ResponseEntity.ok(discountTypeService.getDiscountTypeWithPromotions());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error howing the discount types:"+e.getMessage()); 
        }
    }

    @PostMapping("discountType")
    public ResponseEntity<?> createDiscountType(@Valid @RequestBody DiscountTypeDTO discountTypeDTO){
        try{
            DiscountTypeDTO createdDiscountTypeDTO = discountTypeService.save(discountTypeDTO);
            
            if(createdDiscountTypeDTO == null ){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This DiscountType already exists");
            }
            return ResponseEntity.ok(createdDiscountTypeDTO);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the Discount type:"+e.getMessage());
        }
    }

    @DeleteMapping("discountType/{id}")
    public ResponseEntity<?> deleteDiscountType(@PathVariable long id){
        try{
            DiscountTypeDTO discountTypeDTO = discountTypeService.findById(id);
            if(discountTypeDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The DiscpuntType does not exist");
            }
            
            discountTypeService.delete(discountTypeDTO);
            return ResponseEntity.ok().build(); 
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the discount type:"+e.getMessage()); 
        }
    }


    @PostMapping("discountType/{id}")
    public ResponseEntity<?> updateDicountType(@PathVariable long id, @Valid @RequestBody DiscountType discountType){
        try{
            DiscountTypeDTO existingDiscountTypeDTO = discountTypeService.findById(id);
            if(existingDiscountTypeDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The discount type does not exist");
            }
            DiscountTypeDTO updatedDiscountTypeDTO = discountTypeService.update(existingDiscountTypeDTO, discountType);

            if( updatedDiscountTypeDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entred name already exits");
            }
            return ResponseEntity.ok(updatedDiscountTypeDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the Discount type"+e.getMessage()); 
        }
    }




}
