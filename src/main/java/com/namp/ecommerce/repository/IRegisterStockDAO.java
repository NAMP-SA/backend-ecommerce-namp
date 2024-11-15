package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.RegisterStock;

public interface IRegisterStockDAO extends CrudRepository<RegisterStock,Long>{
    List<RegisterStock> findAll();
    RegisterStock findByIdRegisterStock(long id);
    
}
