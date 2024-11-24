package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.model.Promotion;

public interface IPromotionService {
    List<PromotionDTO> getPromotions();
    List<PromotionWithProductsDTO> getPromotionsWithProducts(); 
    PromotionWithProductsDTO getPromotionsWithProducts(long id);
    PromotionDTO save(PromotionDTO promotionDTO);
    PromotionDTO update(PromotionDTO existingPromotionDTO, Promotion promotion); 
    void delete(PromotionDTO promotionDTO); 
    PromotionDTO findById(long id);
}
