package com.namp.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithRegisterStocksDTO {
    private long idProduct;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String img;


    private List<RegisterStockDTO> registerStocks;

    private double sellingPrice; // Precio luego de aplicar la promoción

}
