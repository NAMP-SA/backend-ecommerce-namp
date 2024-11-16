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

import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.model.Order;
import com.namp.ecommerce.service.IOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class OrderController {
    
    @Autowired
    private IOrderService orderService; 

    @GetMapping("order")
    public ResponseEntity<?> getOrders(){
        try{
            return ResponseEntity.ok(orderService.getOrders());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error showing the orders:"+e.getMessage());
        }
    }

    //El get maping para el OrderWithDetailsOrders 
    // @GetMapping
    // Lo msimo pero con la Id 
    //@GetMapping 

    @PostMapping("order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        try{
            OrderDTO createdOrderDTO = orderService.save(orderDTO); 
            if (createdOrderDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This order already exists.");
            }
            return ResponseEntity.ok(createdOrderDTO);     
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating the order:"+e.getMessage()); 
        }
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id){
        try{
            OrderDTO orderDTO = orderService.findById(id); 
            
            if (orderDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The order does not exist"); 
            }

            orderService.delete(orderDTO);
            return ResponseEntity.ok().build(); 
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro deleting the order:"+e.getMessage());
        }
    }
    
    @PutMapping("order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable long id, @Valid @RequestBody Order order){
        try{
            OrderDTO existingOrderDTO = orderService.findById(id); 
            if (existingOrderDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The order does not exists "); 
            }
            OrderDTO updatedOrderDTO = orderService.update(existingOrderDTO, order); 

            if(updatedOrderDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("The entered order already exists");
            }
            return ResponseEntity.ok(updatedOrderDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating the order:"+e.getMessage()); 
        }
    }

}
