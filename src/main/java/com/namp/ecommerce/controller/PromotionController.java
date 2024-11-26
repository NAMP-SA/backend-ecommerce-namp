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

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.model.Promotion;
import com.namp.ecommerce.service.IPromotionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class PromotionController {
    
    @Autowired
    private IPromotionService promotionService; 

    @GetMapping("promotion")
    public ResponseEntity<?> getPromotions(){
        try{
            return ResponseEntity.ok(promotionService.getPromotions());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the promotions:"+e.getMessage());
        }
    }

    @GetMapping("promotionWithProducts")
    public ResponseEntity<?> getPromotionsWithProducts(){
        try{
            return ResponseEntity.ok(promotionService.getPromotionsWithProducts());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the promotions:"+e.getMessage());
        }
    }

    @GetMapping("promotionWithProducts/{id}")
    public ResponseEntity<?> getPromotionsIdWithProducts(@PathVariable long id){
        try{
            if(promotionService.getPromotionsWithProducts(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Promotion with ID"+id+" not found"); 
            }
            return ResponseEntity.ok(promotionService.getPromotionsWithProducts(id)); 
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the promotions:"+e.getMessage());
        }
    }

    @PostMapping("/admin/promotion")
    public ResponseEntity<?>  cratPromotion(@Valid @RequestBody PromotionDTO promotionDTO){
        try{
            PromotionDTO createdPromotionDTO = promotionService.save(promotionDTO); 
            if(createdPromotionDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This promotion already exists"); 
            }
            return ResponseEntity.ok(createdPromotionDTO); 
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the promotion"+e.getMessage()); 
        }
    }

    @DeleteMapping("/admin/promotion/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable long id){
        try{
            PromotionDTO promotionDTO = promotionService.findById(id);
            if(promotionDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The promotion does not exist"); 
            }
            promotionService.delete(promotionDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the promotion"+e.getMessage()); 
        }
    }

    @PutMapping("/admin/promotion/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable long id, @Valid @RequestBody Promotion promotion){
        try{
            PromotionDTO existingPromotionDTO = promotionService.findById(id); 

            if(existingPromotionDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The promotion does not exist");
            }
            PromotionDTO updatedPromotionDTO = promotionService.update(existingPromotionDTO, promotion); 

            if(updatedPromotionDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entered already exist"); 
            }
            
            return ResponseEntity.ok(updatedPromotionDTO); 
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the promotion"+e.getMessage()); 
        }
        
    }
    





}
