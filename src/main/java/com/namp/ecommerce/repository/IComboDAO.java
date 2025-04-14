package com.namp.ecommerce.repository;

import com.namp.ecommerce.model.Combo;
import com.namp.ecommerce.model.ProductCombo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IComboDAO extends CrudRepository<Combo, Long> {
    List<Combo> findAll();
    Combo findByName(String name);
    Combo findByIdCombo(long id);

    List<ProductCombo> findProductComboByIdCombo(long idCombo);
}
