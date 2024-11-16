package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.RegisterStockDTO;
import com.namp.ecommerce.model.RegisterStock;

public interface IRegisterStockService {
    List<RegisterStockDTO> getRegisterStocks();
    RegisterStockDTO save(RegisterStockDTO registerStockDTO);
    RegisterStockDTO update(RegisterStockDTO existingRegisterStockDTO, RegisterStock registerStock);
    void delete(RegisterStockDTO registerStockDTO);
    RegisterStockDTO findById(long id);
    void deleteWOStock(RegisterStockDTO registerStockDTO);
}
