package com.namp.ecommerce.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboWithDoDTO{

    private long idCombo;
    private String name;
    private String description;
    private String img;
    private double price;

    private List<OrderDetailDTO> orderDetail; 

}
