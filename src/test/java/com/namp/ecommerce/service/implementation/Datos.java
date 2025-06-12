package com.namp.ecommerce.service.implementation;

import java.util.Arrays;
import java.util.List;

import com.namp.ecommerce.dto.CategoryDTO;

public class Datos {
    public final static List<CategoryDTO> EXAMENES = Arrays.asList(
            new CategoryDTO(1L, "Bebidas con alcohol", "Bebidas para mayores de edad"),
            new CategoryDTO(2L, "Bebidas sin alcohol", "Bebidas para cualuiqer persona"));

}
