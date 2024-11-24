package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.mapper.MapperPromotion;
import com.namp.ecommerce.model.Promotion;
import com.namp.ecommerce.repository.IPromotionDAO;
import com.namp.ecommerce.service.IPromotionService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PromotionImplementation implements IPromotionService {

    @Autowired
    private IPromotionDAO promotionDAO; 
    
    @Autowired
    private MapperPromotion mapperPromotion; 
   
    @Override
    public List<PromotionDTO> getPromotions() {
        return promotionDAO.findAll()
                .stream()
                .map(mapperPromotion::convertPromotionToDto)
                .collect(Collectors.toList()); 
    }

    @Override
    public List<PromotionWithProductsDTO> getPromotionsWithProducts() {
      return promotionDAO.findAll()
            .stream()
            .map(mapperPromotion::convertPromotionWithProductsToDto)
            .collect(Collectors.toList()); 
    }
    
    @Override
    public PromotionWithProductsDTO getPromotionsWithProducts(long id) {
        Promotion promotion = promotionDAO.findByIdPromotion(id);

        if(promotion == null){
            return null;
        }
        return mapperPromotion.convePromotionIdWithProductsToDto(promotion);
    }

    @Override
    public PromotionDTO save(PromotionDTO promotionDTO) {

        if (promotionDTO.getDateTimeStart() == null || promotionDTO.getDateTimeEnd() == null) {
            throw new IllegalArgumentException("La fecha de inicio y la fecha de fin no deben ser nulas.");
        }

        if (promotionDTO.getDateTimeEnd().before(promotionDTO.getDateTimeStart())) {
         throw new IllegalArgumentException("The end date must be later than the start date");
        }
        
        Promotion promotion = mapperPromotion.convertDtoToPromotion(promotionDTO);
        Promotion savedPromotion = promotionDAO.save(promotion); 
        return mapperPromotion.convertPromotionToDto(savedPromotion); 
    }

    @Override
    public PromotionDTO update(PromotionDTO existingPromotionDTO, Promotion promotion) {
        //Buscar la Promocioon existente
        Promotion existingPromotion = promotionDAO.findByIdPromotion(existingPromotionDTO.getIdPromotion());
        if(existingPromotion == null){
            return null; 
        }

        // Validar fechas 
        if (promotion.getDateTimeEnd().before(promotion.getDateTimeStart())) {
             throw new IllegalArgumentException("The end date must be later than the start date");
        } 
        //Actualizar los campos 
        existingPromotion.setDiscount(promotion.getDiscount());
        existingPromotion.setDateTimeStart(promotion.getDateTimeStart());
        existingPromotion.setDateTimeEnd(promotion.getDateTimeEnd());
        existingPromotion.setInEffect(promotion.isInEffect()); 

        //Guardar la promocion actualizada
        Promotion updatedPromotion = promotionDAO.save(existingPromotion); 

        //Devolvemos el DTO de la promocion actualizada 
        return mapperPromotion.convertPromotionToDto(updatedPromotion); 

    }

    @Override
    public void delete(PromotionDTO promotionDTO) {
       Promotion promotion = promotionDAO.findByIdPromotion(promotionDTO.getIdPromotion());
       if(promotion == null){
        throw new EntityNotFoundException("Promotion not foundwith ID: "+promotionDTO.getIdPromotion());
       }
       promotionDAO.delete(promotion);
    }

    @Override
    public PromotionDTO findById(long id) {
        Promotion promotion = promotionDAO.findByIdPromotion(id);
        if(promotion == null){
            return null;
        }
        return mapperPromotion.convertPromotionToDto(promotion); 
    }    
    
}
