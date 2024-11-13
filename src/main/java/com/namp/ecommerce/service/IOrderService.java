package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.model.Order;


public interface IOrderService {
    List<OrderDTO> getOrders(); 
    OrderDTO save(OrderDTO orderDTO);
    OrderDTO update(OrderDTO existingOrderDTO, Order order);
    void delete(OrderDTO orderDTO);
    OrderDTO findById(long id);
    boolean verifyName(String normalizedName);
    boolean verifyName(String normalizedName, long idOrder);
}
