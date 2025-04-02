package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.dto.OrderWithDoDTO;
import com.namp.ecommerce.model.Order;

public interface IOrderService {
    List<OrderDTO> getOrders();

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO update(OrderDTO existingOrderDTO, Order order);

    void delete(OrderDTO orderDTO);

    OrderDTO findById(long id);

    OrderWithDoDTO getOrdersIdWithOrderDetails(long id);

    List<OrderWithDoDTO> getOrdersWithOrderDetails();

    void decreaseStocks(OrderDTO orderDTO);

    boolean checkStocks(OrderDTO orderDTO);

    OrderDTO addCoupon(Long idOrder, String couponCode);

    // METODO PROVISIORIO
    OrderDTO confirmOrder(OrderDTO orderDTO);
}
