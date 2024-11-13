package com.namp.ecommerce.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.namp.ecommerce.model.Order;


public interface IOrderDAO extends CrudRepository<Order, Long> {
    List<Order> findAll(); 
    Order findByIdOrder(long id); 
}
