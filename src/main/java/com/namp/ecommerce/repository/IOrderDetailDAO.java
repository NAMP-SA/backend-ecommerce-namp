package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.OrderDetail;

public interface IOrderDetailDAO extends CrudRepository<OrderDetail, Long> {
    List<OrderDetail> findAll(); 
    OrderDetail findByIdDetailOrder(long id); 
}
