package com.namp.ecommerce.dto;



import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    
    private long idPromotion;
    private double discount; //por porcentaje de descuento 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp dateTimeStart; 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp dateTimeEnd; 
    private boolean inEffect; 

    private DiscountTypeDTO idDiscountType; 
}
