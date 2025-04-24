package com.namp.ecommerce.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.CategoryWithSubcategoriesDTO;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.model.Category;

public class CategoryData {

        public final static List<Category> CATEGORIAS = Arrays.asList(
                        new Category(1L, "Bebidas con alcohol", "Bebidas con alcohol", new ArrayList<>()),
                        new Category(2L, "Bebidas sin alcohol", "Bebidas sin alcohol", new ArrayList<>()));

        public final static List<CategoryDTO> CATEGORIASDTO = Arrays.asList(
                        new CategoryDTO(1L, "Bebidas con alcohol", "Bebidas para mayores de edad"),
                        new CategoryDTO(2L, "Bebidas sin alcohol", "Bebidas para cualuiqer persona"));

        public final static CategoryDTO CATEGORIADTO = new CategoryDTO(1L, "Bebidas alcoholicas",
                        "Bebidas con alcohol");

        public final static Category CATEGORIA = new Category(1L, "BEBIDAS ALCOHOLICAS",
                        "Bebidas con alcohol", new ArrayList<>());

        public final static CategoryDTO CATEGORIADTO_ACTUALIZADA = new CategoryDTO(1L,
                        "BEBIDAS ALCOHOLICAS ACTUALIZADA",
                        "Bebidas con alcohol");

        public final static Category CATEGORIA_ACTUALIZADA = new Category(1L, "BEBIDAS ALCOHOLICAS ACTUALIZADA",
                        "Bebidas con alcohol", new ArrayList<>());

        // Get con subcategorias

        public final static CategoryDTO BEBIDAS_ALCOHOL_DTO = new CategoryDTO(1L, "Bebidas con alcohol",
                        "Bebidas con alcohol");
        public final static CategoryDTO BEBIDAS_NO_ALCOHOL_DTO = new CategoryDTO(2L, "Bebidas sin alcohol",
                        "Bebidas sin alcohol");

        public final static List<SubcategoryDTO> SUBCATEGORIAS_ALCOHOL_DTO = Arrays.asList(
                        new SubcategoryDTO(1L, "Vodka", "Bebida blanca", BEBIDAS_ALCOHOL_DTO),
                        new SubcategoryDTO(2L, "Cinzano", "Aperitivo", BEBIDAS_ALCOHOL_DTO));

        public final static List<SubcategoryDTO> SUBCATEGORIAS_NO_ALCOHOL_DTO = Arrays.asList(
                        new SubcategoryDTO(3L, "Gaseosa", "Bebida con gas", BEBIDAS_NO_ALCOHOL_DTO),
                        new SubcategoryDTO(4L, "Jugos", "Jugos de frutas", BEBIDAS_NO_ALCOHOL_DTO));

        public final static CategoryWithSubcategoriesDTO CATEGORIA_SUBCATEGORIAS = new CategoryWithSubcategoriesDTO(1L,
                        "Bebidas alcoholicas", "Bebidas alcoholicas",
                        SUBCATEGORIAS_ALCOHOL_DTO, null);

        public final static List<CategoryWithSubcategoriesDTO> CATEGORIAS_SUBCATEGORIAS = Arrays.asList(
                        new CategoryWithSubcategoriesDTO(1L, "Bebidas alcoholicas", "Bebidas alcoholicas",
                                        SUBCATEGORIAS_ALCOHOL_DTO, null),
                        new CategoryWithSubcategoriesDTO(2L, "Bebidas sin alcohol", "Bebidas sin alcohol",
                                        SUBCATEGORIAS_NO_ALCOHOL_DTO, null));

}
