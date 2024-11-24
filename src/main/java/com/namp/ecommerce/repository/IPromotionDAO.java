package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.Promotion;

public interface IPromotionDAO extends CrudRepository<Promotion, Long> {
    List<Promotion> findAll(); 
    Promotion findByIdPromotion(long id);  
}

