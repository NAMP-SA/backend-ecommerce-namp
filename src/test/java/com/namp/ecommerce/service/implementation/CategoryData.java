package com.namp.ecommerce.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.model.Category;

public class CategoryData {

    public final static List<Category> CATEGORIAS = Arrays.asList(
            new Category(1L, "Bebidas con alcohol", "Bebidas para mayores de edad", new ArrayList<>()),
            new Category(2L, "Bebidas sin alcohol", "Bebidas para cualuiqer persona", new ArrayList<>()));


    public final static List<CategoryDTO> CATEGORIASDTO = Arrays.asList(
        new CategoryDTO(1L, "Bebidas con alcohol", "Bebidas para mayores de edad"),
        new CategoryDTO(2L, "Bebidas sin alcohol", "Bebidas para cualuiqer persona"));

}
