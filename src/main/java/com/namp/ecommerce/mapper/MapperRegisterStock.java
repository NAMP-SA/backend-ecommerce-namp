package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.RegisterStockDTO;
import com.namp.ecommerce.model.RegisterStock;
import com.namp.ecommerce.repository.IProductDAO;

@Component
public class MapperRegisterStock {
    @Autowired 
    IProductDAO productDAO;

    @Autowired MapperUtil mapperUtil;


    public RegisterStock convertDtoToRegisterStock(RegisterStockDTO registerStockDTO){
        RegisterStock registerStock = new RegisterStock();

        registerStock.setDateTime(registerStockDTO.getFechaHora());
        registerStock.setQuantity(registerStockDTO.getQuantity());
        registerStock.setIdProduct(productDAO.findByIdProduct(registerStockDTO.getIdProduct().getIdProduct()));

        return registerStock;
    }

        /*
----------------------------------------------------------------------------------------------------------
                                        MAPPER UTIL CALLS
-----------------------------------------------------------------------------------------------------------
    */

    public RegisterStockDTO convertRegisterStockToDto(RegisterStock registerStock){
        RegisterStockDTO registerStockDTO = mapperUtil.convertRegisterStockToDto(registerStock);
        return registerStockDTO;
    }

}
