package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.model.Order;
import com.namp.ecommerce.repository.IStateDAO;

@Component
public class MapperOrder {
    
    @Autowired
    private IStateDAO stateDAO; 

    @Autowired
    private MapperUtil mapperUtil; 


    //Metodo para convertir de OrderDTO a OrderDTO
    public Order convertDtoToOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setFechaHora(orderDTO.getDateHour());
        order.setIdState(stateDAO.findByIdState(orderDTO.getIdState().getIdState()));
        //order.setIdUSer(); 

        return order;
    }
    
    /* 
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
     */
    public OrderDTO convertOrderToDTO(Order order){
        OrderDTO orderDTO = mapperUtil.convertOrderToDTO(order);
        return orderDTO; 
    }
    
    // metodos para el detalle de los pedidos 
    
    
    
}
