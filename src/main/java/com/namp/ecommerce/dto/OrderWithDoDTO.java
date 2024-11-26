package com.namp.ecommerce.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithDoDTO {
    private long idOrder; 
    private Timestamp dateTime; 
    //private double total;
    private StateDTO idState; 
    //private User user; 
    private List<OrderDetailDTO> orderDetail; 
    

}