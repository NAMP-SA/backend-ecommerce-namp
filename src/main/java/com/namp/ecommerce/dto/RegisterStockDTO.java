package com.namp.ecommerce.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStockDTO {
    private long idRegisterStock;
    private Timestamp fechaHora;
    private int quantity;
    private ProductDTO idProduct; 
}
