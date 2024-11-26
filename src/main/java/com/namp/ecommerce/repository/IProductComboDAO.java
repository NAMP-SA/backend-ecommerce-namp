package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.namp.ecommerce.model.ProductCombo;
import org.springframework.data.repository.query.Param;

public interface IProductComboDAO extends CrudRepository<ProductCombo,Long>{
    List<ProductCombo> findAll();
    ProductCombo findByIdProductCombo(long id);
    @Query("SELECT pc FROM ProductCombo pc WHERE pc.idCombo.idCombo = :idCombo")
    List<ProductCombo> findByComboId(@Param("idCombo") Long idCombo);
}