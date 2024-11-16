package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.model.OrderDetail;
import com.namp.ecommerce.repository.IComboDAO;
import com.namp.ecommerce.repository.IOrderDAO;
import com.namp.ecommerce.repository.IProductDAO;

@Component
public class MapperOrderDetail {
    
    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private IProductDAO productDAO; 

    @Autowired
    private IComboDAO comboDAO; 

    @Autowired
    private MapperUtil mapperUtil; 

    //Metodo para convertir de DetailOrderDETO a DetailOrder 
    public OrderDetail convertDtoToOrderDetail(OrderDetailDTO orderDetailDTO){
        OrderDetail OrderDetail = new OrderDetail(); 

        OrderDetail.setSubTotal(orderDetailDTO.getSubTotal());
        OrderDetail.setQuantity(orderDetailDTO.getQuantity());
        
        if (orderDetailDTO.getIdCombo() == null){
            OrderDetail.setIdProduct(productDAO.findByIdProduct(orderDetailDTO.getIdProduct().getIdProduct()));
        }else{
            OrderDetail.setIdCombo(comboDAO.findByIdCombo(orderDetailDTO.getIdCombo().getIdCombo()));
        }
        
        OrderDetail.setIdOrder(orderDAO.findByIdOrder(orderDetailDTO.getIdOrder().getIdOrder()));
        return OrderDetail; 
    }

    /*
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
     */

    //Metodo para convertir OrderDetail a OrderDetailDTO 
    public OrderDetailDTO convertOrderDetailToDto(OrderDetail OrderDetail){
        OrderDetailDTO orderDetailDTO = mapperUtil.convertOrderDetailToDto(OrderDetail);
        return orderDetailDTO;
    }

}
