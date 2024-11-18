package com.namp.ecommerce.service.implementation;


import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.dto.OrderWithDoDTO;
import com.namp.ecommerce.mapper.MapperOrder;
import com.namp.ecommerce.model.Order;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.repository.IOrderDAO;
import com.namp.ecommerce.repository.IProductDAO;
import com.namp.ecommerce.service.IOrderDetailService;
import com.namp.ecommerce.service.IOrderService;
import com.namp.ecommerce.service.IProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderImplementation implements IOrderService {
    
    @Autowired
    private IOrderDAO orderDAO; 

    @Autowired
    private MapperOrder mapperOrder;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IProductDAO productDAO;

    @Override
    public List<OrderDTO> getOrders() {
        return orderDAO.findAll()
            .stream()
            .map(mapperOrder::convertOrderToDTO)
            .collect(Collectors.toList()); 
    }

    //Listar los detalles del Pedido con getOrdersWhitDetailsOrders() y con ID
    
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
       if(orderDTO != null) { 
        if (orderDTO.getDateHour() == null) {
             orderDTO.setDateHour(new Timestamp(System.currentTimeMillis()));
        }
        // Convertir el DTO a la entidad Order 
        Order order = mapperOrder.convertDtoToOrder(orderDTO); 
        // Guardar la entidad en la base de datos 
        Order savedOrder = orderDAO.save(order); 
        // Convertir la entidad guardada de nuevo a DTO 
        return mapperOrder.convertOrderToDTO(savedOrder); 
    }
     return null;
    }

    @Override
    public OrderDTO update(OrderDTO existingOrderDTO, Order order) {
        // Buscar la orden existente 
        Order existingOrder = orderDAO.findByIdOrder(existingOrderDTO.getIdOrder()); 
        if (existingOrder == null) {
         return null; 
        } 
        // Actualizar los campos de la entidad existente
        if (order.getFechaHora() != null) {
            existingOrder.setFechaHora(order.getFechaHora()); 
        } 
        if (order.getIdState() != null) {
            existingOrder.setIdState(order.getIdState()); 
        } 
        // Guardar la orden actualizada 
        Order updatedOrder = orderDAO.save(existingOrder); 
        // Devolver el DTO de la orden actualizada 
        return mapperOrder.convertOrderToDTO(updatedOrder);
    }

    @Override
    public void delete(OrderDTO orderDTO) {
       Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
       if (order == null){
            throw new EntityNotFoundException("Order not found with ID: " + orderDTO.getIdOrder());
       }
       orderDAO.delete(order);
    }

    @Override
    public OrderDTO findById(long id) {
        Order order = orderDAO.findByIdOrder(id); 
        if (order == null){
            return null; 
        }
        return mapperOrder.convertOrderToDTO(order); 
    }
    
    @Override
    public OrderWithDoDTO getOrdersIdWithOrderDetails(long id) {
        Order order = orderDAO.findByIdOrder(id);
        if (order == null){
            return null;
        }

        return mapperOrder.convertOrderWithOrderDetailToDto(order);
    } 

    @Override
    public void calculateTotal(OrderDTO orderDTO) {

        OrderWithDoDTO orderWithDoDTO = this.getOrdersIdWithOrderDetails(orderDTO.getIdOrder());
        double total = orderWithDoDTO.getOrderDetail().stream()
                                      .mapToDouble(OrderDetailDTO::getSubTotal)
                                      .sum();
        System.out.println(total);
    }   

    @Override
    public List<OrderWithDoDTO> getOrdersWithOrderDetails(){
        return orderDAO.findAll()
                .stream()
                .map(mapperOrder::convertOrderWithOrderDetailToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStocks(OrderDTO orderDTO) {

        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        OrderWithDoDTO orderWithDoDTO = mapperOrder.convertOrderWithOrderDetailToDto(order);
        for (OrderDetailDTO orderDetail : orderWithDoDTO.getOrderDetail()) {
            if (orderDetail.getIdProduct() != null){
                orderDetailService.decreaseStockProduct(orderDetail, orderDetail.getIdProduct());
            }
            if (orderDetail.getIdCombo() != null){
                orderDetailService.decreaseStockCombo(orderDetail, orderDetail.getIdCombo());
            }
            
        }

    }

    @Override
    public boolean checkStocks(OrderDTO orderDTO){
        List<Product> products = productDAO.findAll();
        for (Product product : products){
            product.setSimulatedStock(product.getStock());
        }
        
        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        OrderWithDoDTO orderWithDoDTO = mapperOrder.convertOrderWithOrderDetailToDto(order);
        for (OrderDetailDTO orderDetail : orderWithDoDTO.getOrderDetail()) {
            if (orderDetail.getIdProduct() != null){
                if(orderDetailService.checkStockProduct(orderDetail, orderDetail.getIdProduct())==false){
                    return false;
                };
            }
            if (orderDetail.getIdCombo() != null){
               if(orderDetailService.checkStockCombo(orderDetail, orderDetail.getIdCombo())==false){
                return false;
               };
            }
      
        }

        return true; 
    }


    //METODO PROVISIORIO 

    @Override 
    public void confirmOrder(OrderDTO orderDTO){
        this.calculateTotal(orderDTO);
        this.decreaseStocks(orderDTO);
    }

    

}
