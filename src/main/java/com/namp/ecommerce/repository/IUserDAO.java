package com.namp.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserDAO extends CrudRepository<User,Long>{
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User findByIdUser(long id);
}
