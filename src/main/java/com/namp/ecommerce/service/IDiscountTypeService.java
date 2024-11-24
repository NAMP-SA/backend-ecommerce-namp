package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.DiscountTypeDTO;
import com.namp.ecommerce.dto.DiscountTypeWithPromotionDTO;
import com.namp.ecommerce.model.DiscountType;

public interface IDiscountTypeService {
    List<DiscountTypeDTO> getDiscountTypes(); 
    List<DiscountTypeWithPromotionDTO> getDiscountTypeWithPromotions(); 
    
    DiscountTypeDTO save(DiscountTypeDTO discountTypeDTO); 
    DiscountTypeDTO update(DiscountTypeDTO existingDiscountType, DiscountType DiscountType); 
    void delete(DiscountTypeDTO discountTypeDTO); 
    DiscountTypeDTO findById(long id); 
    boolean verifyName(String normalizedName);
    boolean verifyName(String normalizedName, long idDiscuntType);
}
