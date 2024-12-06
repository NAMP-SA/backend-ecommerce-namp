package com.namp.ecommerce.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.State;

public interface IStateDAO extends CrudRepository<State, Long> {
    List<State> findAll();
    State findByIdState(long id);
}

