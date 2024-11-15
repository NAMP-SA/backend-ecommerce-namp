package com.namp.ecommerce.service.implementation;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.RegisterStockDTO;
import com.namp.ecommerce.model.RegisterStock;
import com.namp.ecommerce.repository.IRegisterStockDAO;
import com.namp.ecommerce.service.IProductService;
import com.namp.ecommerce.service.IRegisterStockService;

import jakarta.persistence.EntityNotFoundException;

import com.namp.ecommerce.mapper.MapperRegisterStock;
@Service
public class RegisterStockImplementation implements IRegisterStockService {

    @Autowired
    private IRegisterStockDAO registerStockDAO;

    @Autowired
    private MapperRegisterStock mapperRegisterStock;

    @Autowired
    private IProductService productService;


    @Override
    public List<RegisterStockDTO> getRegisterStocks() {
        return registerStockDAO.findAll()
                .stream()
                .map(mapperRegisterStock::convertRegisterStockToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RegisterStockDTO save(RegisterStockDTO registerStockDTO) {

        if (registerStockDTO != null){
            if (registerStockDTO.getFechaHora() == null){
                registerStockDTO.setFechaHora(new Timestamp(System.currentTimeMillis()));
            }
        }
        productService.increaseStock(registerStockDTO.getIdProduct(), registerStockDTO.getQuantity());
        RegisterStock registerStock = mapperRegisterStock.convertDtoToRegisterStock(registerStockDTO);
        RegisterStock savedRegisterStock = registerStockDAO.save(registerStock);
        return mapperRegisterStock.convertRegisterStockToDto(savedRegisterStock);
    }

    @Override
    public RegisterStockDTO update(RegisterStockDTO existingRegisterStockDTO, RegisterStock registerStock) {
        RegisterStock existingRegisterStock = registerStockDAO.findByIdRegisterStock(existingRegisterStockDTO.getIdRegisterStock());

        if (existingRegisterStock==null){
            return null;
        }
        if (registerStock.getDateTime() == null){
            existingRegisterStock.setDateTime(new Timestamp(System.currentTimeMillis()));
        }
        RegisterStockDTO registerStockDTO = mapperRegisterStock.convertRegisterStockToDto(registerStock); //Hay que corregir esto 
        
        if(registerStockDTO.getIdProduct()==existingRegisterStockDTO.getIdProduct()){
            System.out.println("Entro al if");
            if(existingRegisterStock.getQuantity()<registerStock.getQuantity()){
            
                int difference = registerStock.getQuantity() - existingRegisterStock.getQuantity();
                productService.increaseStock(registerStockDTO.getIdProduct(), difference);
            }
            if(existingRegisterStock.getQuantity()>registerStock.getQuantity()){
                int difference = existingRegisterStock.getQuantity() - registerStock.getQuantity();
                productService.decraseStock(registerStockDTO.getIdProduct(), difference);
            }
        }else{
            System.out.println("Entro al else");
            System.out.println(existingRegisterStockDTO.getQuantity());
            productService.decraseStock(existingRegisterStockDTO.getIdProduct(), existingRegisterStockDTO.getQuantity());
            productService.increaseStock(registerStockDTO.getIdProduct(),registerStockDTO.getQuantity());
        }

        
        existingRegisterStock.setQuantity(registerStock.getQuantity());
        existingRegisterStock.setIdProduct(registerStock.getIdProduct());
        RegisterStock updatedRegisterStock = registerStockDAO.save(existingRegisterStock);
        
        return mapperRegisterStock.convertRegisterStockToDto(updatedRegisterStock);

    }

    @Override
    public void delete(RegisterStockDTO registerStockDTO) {
        RegisterStock registerStock = registerStockDAO.findByIdRegisterStock(registerStockDTO.getIdRegisterStock());
        if (registerStock==null){
            throw new EntityNotFoundException("Register Stock not found with ID: " + registerStockDTO.getIdRegisterStock());

        }
        productService.decraseStock(registerStockDTO.getIdProduct(), registerStockDTO.getQuantity());
        registerStockDAO.delete(registerStock);
    }

    @Override
    public void deleteWOStock(RegisterStockDTO registerStockDTO) {
        RegisterStock registerStock = registerStockDAO.findByIdRegisterStock(registerStockDTO.getIdRegisterStock());
        if (registerStock==null){
            throw new EntityNotFoundException("Register Stock not found with ID: " + registerStockDTO.getIdRegisterStock());

        }
        registerStockDAO.delete(registerStock);
    }

    @Override
    public RegisterStockDTO findById(long id) {
        RegisterStock registerStock = registerStockDAO.findByIdRegisterStock(id);
        if (registerStock==null){
            return null;
        }
        return mapperRegisterStock.convertRegisterStockToDto(registerStock);
    }

    
}
