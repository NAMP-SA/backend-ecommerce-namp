package com.namp.ecommerce.dto;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionWithProductsDTO {
    private long idPromotion;
    private double discount; 
    private Timestamp dateHourStart; 
    private Timestamp dateHourEnd; 
    private boolean inEffect; 

    private DiscountTypeDTO idDiscountType; 

    private List<ProductDTO> products;
}
