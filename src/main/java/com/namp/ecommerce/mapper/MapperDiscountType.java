package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.DiscountTypeDTO;
import com.namp.ecommerce.dto.DiscountTypeWithPromotionDTO;
import com.namp.ecommerce.model.DiscountType;


@Component
public class MapperDiscountType {
    
    @Autowired
    private MapperUtil mapperUtil; 

    //Metodo para mappear de DiscountTypeDTO a DiscountType 
    public DiscountType convertDtoToDiscountType(DiscountTypeDTO discountTypeDTO){
        DiscountType discountType = new DiscountType(); 
        discountType.setName(discountTypeDTO.getName());
        return discountType; 
    }

    /*
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
    */

    public DiscountTypeDTO convertDiscountTypeToDTO(DiscountType discountType){
        DiscountTypeDTO discountTypeDTO = mapperUtil.convertDiscountTypeToDTO(discountType); 
        return discountTypeDTO; 
    }

    public DiscountTypeWithPromotionDTO convertDiscountTypeWithPrmotionsToDTO(DiscountType discountType){
        DiscountTypeWithPromotionDTO discountTypeWithPromotionDTO = mapperUtil.convertDiscountTypeWithPromtionsToDTO(discountType); 
        return discountTypeWithPromotionDTO; 
    }

     


}
