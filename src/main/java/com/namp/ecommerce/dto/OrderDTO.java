package com.namp.ecommerce.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long idOrder; 
    private Timestamp dateTime; 
    private StateDTO idState; 
    private UserDTO idUser; 

}




