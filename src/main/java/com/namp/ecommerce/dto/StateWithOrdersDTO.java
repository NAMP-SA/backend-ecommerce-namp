package com.namp.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateWithOrdersDTO {
    
    private long idState; 
    private String name; 

    private List<OrderDTO> ordes; 
}


