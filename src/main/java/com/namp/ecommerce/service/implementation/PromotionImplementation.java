package com.namp.ecommerce.service.implementation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.mapper.MapperPromotion;
import com.namp.ecommerce.model.Product;
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

        //Normalizar los espacios los espacios y convertir a mayusculas
        String nomralizedName = promotionDTO.getName().replaceAll("\\s+", " ").trim().toUpperCase();

        if( !verifyName(nomralizedName)){
            

            if (promotionDTO.getDateTimeStart() == null || promotionDTO.getDateTimeEnd() == null) {
                throw new IllegalArgumentException("The start date and the end date must not be void.");
            }
    
            if (promotionDTO.getDateTimeEnd().before(promotionDTO.getDateTimeStart())) {
             throw new IllegalArgumentException("The end date must be later than the start date");
            }
            
            Timestamp now = Timestamp.from(Instant.now());
            if (promotionDTO.getDateTimeStart().before(now)) {
                throw new IllegalArgumentException("The start date must be later than the current date");
            }

            promotionDTO.setName(nomralizedName);

            //Convertir el DTO a entidad
            Promotion promotion = mapperPromotion.convertDtoToPromotion(promotionDTO);

            
            Promotion savedPromotion = promotionDAO.save(promotion); 
            return mapperPromotion.convertPromotionToDto(savedPromotion); 
        
        }
        return null; 

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
                
        //Normalizar los espacios y convertir a mayusculas
        String nomralizedName = promotion.getName().replaceAll("\\s+", " ").trim().toUpperCase();
        //Verficar que el nombre este disponible 
        if(verifyName(nomralizedName, existingPromotionDTO.getIdPromotion())){
            return null;
        }

        
        //Actualizar los campos 
        existingPromotion.setDiscount(promotion.getDiscount());
        existingPromotion.setName(nomralizedName); 
        existingPromotion.setDateTimeStart(promotion.getDateTimeStart());
        existingPromotion.setDateTimeEnd(promotion.getDateTimeEnd());
        //existingPromotion.setInEffect(promotion.isInEffect()); 

       

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

    @Override
    public boolean verifyName(String normalizedName) {
     List<Promotion> promotions= promotionDAO.findAll();
     String name = normalizedName.replaceAll("\\s+", "");

     //Comparar el nombre de la promocion que se quiere guardar, contra todos los demas
     for(Promotion promotion : promotions){
        if(name.equals(promotion.getName().replaceAll("\\s+", ""))){
            return true;
        }
     }
     return false; 
    }

    @Override
    public boolean verifyName(String normalizedName, long idPromotion) {
        List<Promotion> promotions= promotionDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");
        //Comparar el nombre de la promocion que se quiere guardar, contra todos los demas
        for(Promotion promotion : promotions){
            if(promotion.getIdPromotion() != idPromotion && name.equals(promotion.getName().replaceAll("\\s+", ""))){
                return true;
            }
        }
        return false; 
    }    

    //Metodo que devuelve un True en caso de que la promocion este vigente, false en caso contrario
    //Se considera vigente si la fecha y hora actual es mayor o igual a la fecha y hora de inicio y menor o igual a la fecha y hora de fin
    public boolean isPromotionInEffect(Promotion promotion) {
        if (promotion.getDateTimeStart() == null || promotion.getDateTimeEnd() == null) {
            return false;
        }
        Timestamp now = Timestamp.from(Instant.now());
        return !now.before(promotion.getDateTimeStart()) && !now.after(promotion.getDateTimeEnd());
    }

    //Metodo que calcula el precio de venta de un producto, aplicando la promocion si esta vigente
    public double calculateSellingPrice(Product product) {
        if (product.getIdPromotion() != null) {
            Promotion promotion = product.getIdPromotion();
            if (isPromotionInEffect(promotion)) {
                return product.getPrice() - (product.getPrice() * promotion.getDiscount() / 100);
            }
        }
        return product.getPrice();
    }

    //Metodo que devuelve una lista de promociones vigentes (Activas y futuras, siempre y cuando no hayan expirado)
    @Override
    public List<PromotionDTO> getValidPromotions() {
       Timestamp now = Timestamp.from(Instant.now());

       return promotionDAO.findAll()
                .stream()
                .filter(promotion -> promotion.getDateTimeEnd() != null &&
                                     promotion.getDateTimeEnd().after(now))
                .filter(promotion -> isPromotionInEffect(promotion) ||
                                     promotion.getDateTimeStart().after(now))
                .map(mapperPromotion::convertPromotionToDto)
                .collect(Collectors.toList());
    }
    
}
