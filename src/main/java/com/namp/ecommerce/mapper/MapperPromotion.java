package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.model.Promotion;

@Component
public class MapperPromotion {
    

    @Autowired
    private MapperUtil mapperUtil;


    //Metodo para convertir del PromotionDTO a Peomotion
    public Promotion convertDtoToPromotion(PromotionDTO promotionDTO){
        Promotion promotion = new Promotion(); 

        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setName(promotionDTO.getName());
        promotion.setDateTimeStart(promotionDTO.getDateTimeStart());
        promotion.setDateTimeEnd(promotionDTO.getDateTimeEnd());
        //promotion.setInEffect(promotionDTO.isInEffect());
       
        return promotion; 
    }

    /* 
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
    */
    
    public PromotionDTO convertPromotionToDto(Promotion promotion){
        PromotionDTO promotionDTO = mapperUtil.convertPromotionToDTO(promotion); 
        return promotionDTO; 
    }
    
    public PromotionWithProductsDTO convertPromotionWithProductsToDto(Promotion promotion){
        PromotionWithProductsDTO promotionWithProductsDTO = mapperUtil.convertPromotionWithProductsDto(promotion);
        return promotionWithProductsDTO;
    }

    public PromotionWithProductsDTO convePromotionIdWithProductsToDto(Promotion promotion){
        PromotionWithProductsDTO promotionIdWithProductsDTO = mapperUtil.convertPromotionIdWithProductsDto(promotion);
        return promotionIdWithProductsDTO; 
    }

}
