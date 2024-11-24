package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.DiscountTypeDTO;
import com.namp.ecommerce.dto.DiscountTypeWithPromotionDTO;
import com.namp.ecommerce.mapper.MapperDiscountType;
import com.namp.ecommerce.model.DiscountType;
import com.namp.ecommerce.repository.IDiscountTypeDAO;
import com.namp.ecommerce.service.IDiscountTypeService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountTypeImplementation implements IDiscountTypeService {

    @Autowired
    private IDiscountTypeDAO discountTypeDAO; 

    @Autowired
    private MapperDiscountType mapperDiscountType; 


    @Override
    public List<DiscountTypeDTO> getDiscountTypes() {
       return discountTypeDAO.findAll()
            .stream()
            .map(mapperDiscountType::convertDiscountTypeToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<DiscountTypeWithPromotionDTO> getDiscountTypeWithPromotions() {
       return discountTypeDAO.findAll()
            .stream()
            .map(mapperDiscountType::convertDiscountTypeWithPrmotionsToDTO)
            .collect(Collectors.toList()); 
    } 

    @Override
    public DiscountTypeDTO save(DiscountTypeDTO discountTypeDTO) {
        //Normalizar los espacios en blanco y convertir a mayusculas 
        String normalizedName = discountTypeDTO.getName().replaceAll("\\s+", " ").trim().toUpperCase();

         if(!verifyName(normalizedName)){
            discountTypeDTO.setName(normalizedName);

            DiscountType discountType = mapperDiscountType.convertDtoToDiscountType(discountTypeDTO);
            DiscountType savedState = discountTypeDAO.save(discountType); 
            return mapperDiscountType.convertDiscountTypeToDTO(savedState); 
        }
        return null; 

    }

    @Override
    public DiscountTypeDTO update(DiscountTypeDTO existingDiscountTypeDTO, DiscountType discountType) {
        //Buscar el Tipo de descuento exitente 
        DiscountType existingDiscountType = discountTypeDAO.findByIdDiscountType(existingDiscountTypeDTO.getIdDiscountType());
        if(existingDiscountType == null){
            return null; 
        }
        //Normalizar los espacios en blanco y convertir a mayusculas 
        String normalizedName = discountType.getName().replaceAll("\\s+", " ").trim().toUpperCase();

        //Verficar que el nombre este disponible 
        if(verifyName(normalizedName, existingDiscountTypeDTO.getIdDiscountType())){
            return null; 
        }

        //Actualizar los campos de la entidad
        existingDiscountType.setName(normalizedName);
        
        DiscountType updatedDiscountType = discountTypeDAO.save(existingDiscountType); 

        return mapperDiscountType.convertDiscountTypeToDTO(updatedDiscountType); 

    }

    @Override
    public void delete(DiscountTypeDTO discountTypeDTO) {
        DiscountType discountType = discountTypeDAO.findByIdDiscountType(discountTypeDTO.getIdDiscountType()); 
        if (discountType == null){
            throw new EntityNotFoundException("Discount Typw not found with ID:"+discountTypeDTO.getIdDiscountType()); 
        }
        discountTypeDAO.delete(discountType);
    }

    @Override
    public DiscountTypeDTO findById(long id) {
      DiscountType discountType = discountTypeDAO.findByIdDiscountType(id);
      if (discountType == null){
        return null; 
      }
      return mapperDiscountType.convertDiscountTypeToDTO(discountType);
    }

    @Override
    public boolean verifyName(String normalizedName) {
        List<DiscountType> discountTypes = discountTypeDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");
        //Comparar el nombre del Tipo de descuento que se quiere guardar, con todos lo demas sin espacios 
        for (DiscountType discountType : discountTypes){
            if(name.equals(discountType.getName().replaceAll("\\s+", ""))){
                return true;
            }
        }
        return false; 
    }

    @Override
    public boolean verifyName(String normalizedName, long idDiscuntType) {
        List<DiscountType> discountTypes = discountTypeDAO.findAll(); 
        String name = normalizedName.replaceAll("\\s+", "");
        //Comparar el nombre del Tipo de descuento que se quiere guardar, con todos lo demas sin espacios 
        for (DiscountType discountType : discountTypes){
            if(discountType.getIdDiscountType() != idDiscuntType && name.equals(discountType.getName().replaceAll("\s+", ""))){
                return true;
            }
        }
        return false; 
    }
    
}
