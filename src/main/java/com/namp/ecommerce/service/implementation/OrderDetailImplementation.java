package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.ComboDTO;
import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.dto.ProductDTO;
import com.namp.ecommerce.mapper.MapperOrderDetail;
import com.namp.ecommerce.model.OrderDetail;
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
        List<OrderDetail> orderDetails = orderDetailDAO.findAll();
        for (OrderDetail orderDetailBD : orderDetails) {
          // Comparar productos
          if (orderDetail.getIdProduct() != null && orderDetailBD.getIdProduct() != null &&
              orderDetail.getIdOrder().equals(orderDetailBD.getIdOrder()) &&
              orderDetail.getIdProduct().equals(orderDetailBD.getIdProduct())) {
              return null; // Producto duplicado en el mismo pedido
          }
      
          // Comparar combos
          if (orderDetail.getIdCombo() != null && orderDetailBD.getIdCombo() != null &&
              orderDetail.getIdOrder().equals(orderDetailBD.getIdOrder()) &&
              orderDetail.getIdCombo().equals(orderDetailBD.getIdCombo())) {
              return null; // Combo duplicado en el mismo pedido
          }
        }
      

        if (orderDetailDTO.getIdProduct() == null){
          ComboDTO comboDTO = comboService.findById(orderDetailDTO.getIdCombo().getIdCombo()); 
          orderDetail.setSubTotal(this.CalculateSubTotalCombo(orderDetailDTO.getQuantity(), comboDTO.getPrice())); 
          
        }else{
          ProductDTO productDTO = productService.findById(orderDetailDTO.getIdProduct().getIdProduct());
          orderDetail.setSubTotal(this.CalculateSubTotalProduct(orderDetailDTO.getQuantity(),productDTO.getPrice(),productDTO)); 
        }

        OrderDetail savedOrderDetail = orderDetailDAO.save(orderDetail);
        return mapperOrderDetail.convertOrderDetailToDto(savedOrderDetail);
    }

    @Override
    public OrderDetailDTO update(OrderDetailDTO existingOrderDetailDTO, OrderDetail orderDetail) {
        // Buscar el Detalle existente
        OrderDetail existingOrderDetail = orderDetailDAO.findByIdDetailOrder(existingOrderDetailDTO.getIdOrderDetail());
        if (existingOrderDetail == null) {
            return null;
        }
    
        // Validar duplicados antes de actualizar
        List<OrderDetail> orderDetails = orderDetailDAO.findAll(); // O filtrar por pedido si es posible
        for (OrderDetail orderDetailBD : orderDetails) {
            // Validar duplicados de productos
            if (orderDetail.getIdProduct() != null && orderDetailBD.getIdProduct() != null &&
                orderDetail.getIdOrder().equals(orderDetailBD.getIdOrder()) &&
                orderDetail.getIdProduct().equals(orderDetailBD.getIdProduct())) {
                return null; // Producto duplicado detectado
            }
    
            // Validar duplicados de combos
            if (orderDetail.getIdCombo() != null && orderDetailBD.getIdCombo() != null &&
                orderDetail.getIdOrder().equals(orderDetailBD.getIdOrder()) &&
                orderDetail.getIdCombo().equals(orderDetailBD.getIdCombo())) {
                return null; // Combo duplicado detectado
            }
        }
    
        // Actualizar los campos de la entidad
        double subTotal = 0; 
        existingOrderDetail.setIdCombo(orderDetail.getIdCombo());
        existingOrderDetail.setIdOrder(orderDetail.getIdOrder());
        existingOrderDetail.setIdProduct(orderDetail.getIdProduct());
        existingOrderDetail.setQuantity(orderDetail.getQuantity());
    
        // Calcular el subtotal
        if (orderDetail.getIdProduct() == null) {
            ComboDTO comboDTO = comboService.findById(orderDetail.getIdCombo().getIdCombo()); 
            subTotal += this.CalculateSubTotalCombo(orderDetail.getQuantity(), comboDTO.getPrice()); 
        } else {
            ProductDTO productDTO = productService.findById(orderDetail.getIdProduct().getIdProduct());
            subTotal += this.CalculateSubTotalProduct(orderDetail.getQuantity(), productDTO.getPrice(), productDTO); 
        }
        existingOrderDetail.setSubTotal(subTotal);
    
        // Guardar el detalle actualizado
        OrderDetail updatedOrderDetail = orderDetailDAO.save(existingOrderDetail);
    
        // Devolver el DTO actualizado
        return mapperOrderDetail.convertOrderDetailToDto(updatedOrderDetail);
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
    public double CalculateSubTotalProduct(Integer quantity, double productPrice, ProductDTO productDTO) {
      // double finalPrice;

      // if(productDTO.getIdPromotion() != null && productDTO.getIdPromotion().isInEffect()){
      //   finalPrice = productPrice - (productPrice * productDTO.getIdPromotion().getDiscount() / 100);
      //   System.out.println("SubTotal del producto con promocion"+finalPrice);
      // }else{
      //   finalPrice = productPrice; // Si no hay promoción, emplea el precio base
      // }
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

    @Override
    public boolean checkStockProduct(OrderDetailDTO orderDetailDTO, ProductDTO productDTO) {
      int quantity = orderDetailDTO.getQuantity();
      return(productService.checkStock(productDTO,quantity));
    }

    @Override
    public boolean checkStockCombo(OrderDetailDTO orderDetailDTO, ComboDTO comboDTO) {
      int quantity = orderDetailDTO.getQuantity();
      return(comboService.checkStock(comboDTO,quantity));

    }
}


