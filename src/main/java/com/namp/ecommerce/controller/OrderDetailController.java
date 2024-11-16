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

import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.model.OrderDetail;
import com.namp.ecommerce.service.IOrderDetailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class OrderDetailController {
    
    @Autowired
    private IOrderDetailService orderDetailService; 

    @GetMapping("orderDetail")
    public ResponseEntity<?> getOrderDetail(){
        try{
            return ResponseEntity.ok(orderDetailService.getOderDetails()); 
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error showing the Order Detail:"+e.getMessage()); 
        }
    }

    @PostMapping("orderDetail")
    public ResponseEntity<?> addOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        try{
            OrderDetailDTO createdOderDetailDTO = orderDetailService.save(orderDetailDTO);
            
            if (createdOderDetailDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This Order Detail akready exist"); 
            } 

            return ResponseEntity.ok(createdOderDetailDTO); 
        } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the Order Detail:"+e.getMessage()); 
        }
    }

    @DeleteMapping("orderDetail/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable long id){
        try{
            OrderDetailDTO orderDetailDTO = orderDetailService.findById(id); 

            if(orderDetailDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The Order Detail does not exist"); 
            }
            orderDetailService.delete(orderDetailDTO);
            return ResponseEntity.ok(orderDetailDTO); 
        }   catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting the Order Detail:"+e.getMessage()); 
        }
    }

    @PutMapping("orderDetail/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable long id, @Valid @RequestBody OrderDetail orderDetail){
        try{
            OrderDetailDTO existingOrderDetailDTO = orderDetailService.findById(id);

            if (existingOrderDetailDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The Order Detail does not exist");
            }

            OrderDetailDTO updatedOrderDetailDTO = orderDetailService.update(existingOrderDetailDTO, orderDetail); 
            if(updatedOrderDetailDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This Order Detail already exist"); 
            }
            return ResponseEntity.ok(updatedOrderDetailDTO); 
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating the order Detail:"+e.getMessage()); 
        }
    }

}
