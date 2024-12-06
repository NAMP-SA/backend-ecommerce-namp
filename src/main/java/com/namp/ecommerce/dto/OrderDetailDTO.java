package com.namp.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    
    private long idOrderDetail; 
    private double subTotal; 
    private int quantity; 
    private ProductDTO idProduct; 
    private ComboDTO idCombo;
    private OrderDTO idOrder; 


}
