package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.DiscountType;

public interface IDiscountTypeDAO extends CrudRepository<DiscountType, Long> {
    List<DiscountType> findAll(); 
    DiscountType findByIdDiscountType(long id); 
}
