package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.ComboDTO;
import com.namp.ecommerce.dto.ComboWithItDTO;
import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.mapper.MapperOrderDetail;
import com.namp.ecommerce.model.Combo;
import com.namp.ecommerce.model.OrderDetail;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.repository.IOrderDetailDAO;
import com.namp.ecommerce.service.IComboService;
import com.namp.ecommerce.service.IOrderDetailService;
import com.namp.ecommerce.service.IProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderDetailImplementation implements IOrderDetailService {

    @Autowired
    private IOrderDetailDAO orderDetailDAO;

    @Autowired
    private IProductService productService;

    @Autowired 
    private IComboService comboService; 

    
    @Autowired
    private MapperOrderDetail mapperOrderDetail;


    @Override
    public List<OrderDetailDTO> getOderDetails() {
        return orderDetailDAO.findAll()
            .stream()
            .map(mapperOrderDetail::convertOrderDetailToDto)
            .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = mapperOrderDetail.convertDtoToOrderDetail(orderDetailDTO);
        if (orderDetailDTO.getIdProduct() == null){
          ComboDTO comboDTO = comboService.findById(orderDetailDTO.getIdCombo().getIdCombo()); 
          orderDetail.setSubTotal(this.CalculateSubTotalCombo(orderDetailDTO.getQuantity(), comboDTO.getPrice())); 
          
        }else{
          ProductDTO productDTO = productService.findById(orderDetailDTO.getIdProduct().getIdProduct());
          orderDetail.setSubTotal(this.CalculateSubTotalProduct(orderDetailDTO.getQuantity(),productDTO.getPrice())); 
        }

        OrderDetail savedOrderDetail = orderDetailDAO.save(orderDetail);
        return mapperOrderDetail.convertOrderDetailToDto(savedOrderDetail);
    }

    @Override
    public OrderDetailDTO update(OrderDetailDTO existingOrderDetailDTO, OrderDetail orderDetail) {
      //Buscar el Detalle existente
      OrderDetail existingOrderDetail = orderDetailDAO.findByIdDetailOrder(existingOrderDetailDTO.getIdOrderDetail());
      if (existingOrderDetail == null){
            return null;
      }

      //variable Subtotal
      double subTotal = 0; 

      //Actualizar los campos de la entidad
      existingOrderDetail.setIdCombo(orderDetail.getIdCombo());
      existingOrderDetail.setIdOrder(orderDetail.getIdOrder());
      existingOrderDetail.setIdProduct(orderDetail.getIdProduct());

      existingOrderDetail.setQuantity(orderDetail.getQuantity());

      OrderDetailDTO orderDetailDTO = mapperOrderDetail.convertOrderDetailToDto(orderDetail); 

      if (orderDetailDTO.getIdProduct() == null){
        ComboDTO comboDTO = comboService.findById(orderDetailDTO.getIdCombo().getIdCombo()); 
        subTotal += this.CalculateSubTotalCombo(orderDetailDTO.getQuantity(), comboDTO.getPrice()); 
        
      }else{
        ProductDTO productDTO = productService.findById(orderDetailDTO.getIdProduct().getIdProduct());
        subTotal += this.CalculateSubTotalProduct(orderDetailDTO.getQuantity(),productDTO.getPrice()); 
      }

      existingOrderDetail.setSubTotal(subTotal);

      //Guardamos el detalle
      OrderDetail updateOrderDetail = orderDetailDAO.save(existingOrderDetail);

      //Devolvemos el DTO actaulizado
      return mapperOrderDetail.convertOrderDetailToDto(updateOrderDetail);

    }

    @Override
    public void delete(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailDAO.findByIdDetailOrder(orderDetailDTO.getIdOrderDetail());
        if(orderDetail == null){
            throw new EntityNotFoundException("Order Detail is not found with ID:"+orderDetailDTO.getIdOrderDetail());
        }
        orderDetailDAO.delete(orderDetail);
    }

    @Override
    public OrderDetailDTO findById(long id) {
      OrderDetail orderDetail = orderDetailDAO.findByIdDetailOrder(id);
      if(orderDetail == null){
        return null;
      }
      return mapperOrderDetail.convertOrderDetailToDto(orderDetail);
    }

    @Override
    public double CalculateSubTotalProduct(Integer quantity, double productPrice) {
      return quantity * productPrice;  
      
    }

    @Override
    public double CalculateSubTotalCombo(Integer quantity, double comboPrice) {
      return quantity * comboPrice; 
    }

    @Override 
    public void decreaseStockProduct(OrderDetailDTO orderDetailDTO,ProductDTO productDTO){
      int quantity = orderDetailDTO.getQuantity();
      productService.decreaseStock(productDTO,quantity);
    }

    @Override
    public void decreaseStockCombo(OrderDetailDTO orderDetailDTO,ComboDTO comboDTO){
      int quantity = orderDetailDTO.getQuantity();
      comboService.decreaseStock(comboDTO,quantity);

    }
}


