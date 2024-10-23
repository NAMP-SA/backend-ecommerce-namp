package com.namp.ecommerce.repository;

import com.namp.ecommerce.model.Category;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserDAO extends CrudRepository<User, Long> {
    List<User> findAll();
    User findByIdUser(long id);
}
