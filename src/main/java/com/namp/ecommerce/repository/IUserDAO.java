package com.namp.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.User;

public interface IUserDAO extends CrudRepository<User,Long>{
    Optional<User> findByUsername(String username); 
}
