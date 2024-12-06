package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.ComboDTO;
import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.model.OrderDetail;

public interface IOrderDetailService {

    List<OrderDetailDTO> getOderDetails(); 
    OrderDetailDTO save(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO update(OrderDetailDTO existingOrderDetailDTO, OrderDetail orderDetail);
    void delete(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO findById(long id); 
    
    //Sumar Productos o Combos
    double CalculateSubTotalProduct(Integer quantity, double productPrice, ProductDTO productDTO);
    double CalculateSubTotalCombo(Integer quantity, double comboPrice);  

    boolean checkStockProduct(OrderDetailDTO orderDetailDTO,ProductDTO productDTO);
    boolean checkStockCombo(OrderDetailDTO orderDetailDTO,ComboDTO comboDTO);

    void decreaseStockProduct(OrderDetailDTO orderDetailDTO,ProductDTO productDTO);
    void decreaseStockCombo(OrderDetailDTO orderDetailDTO,ComboDTO comboDTO);
}
