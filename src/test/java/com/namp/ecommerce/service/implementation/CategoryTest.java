package com.namp.ecommerce.service.implementation;


import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.mapper.MapperCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


import com.namp.ecommerce.repository.ICategoryDAO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {

    @Mock
    ICategoryDAO repository;

    @Mock
    MapperCategory mapperCategory;

    @InjectMocks
    CategoryImplementation service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCategoryById() {
        when(repository.findByIdCategory(1L)).thenReturn(CategoryData.CATEGORIAS.get(0));
        when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIAS.get(0))).thenReturn(CategoryData.CATEGORIASDTO.get(0));

        CategoryDTO category = service.findById(1L);

        assertNotNull(category);
        assertEquals(1L, category.getIdCategory());
        assertEquals("Bebidas con alcohol", category.getName());
    }

    @Test
    void findCategories() {
        when(repository.findAll()).thenReturn(CategoryData.CATEGORIAS);

        for (int i = 0; i < (CategoryData.CATEGORIAS.size()); i++){
            when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIAS.get(i))).thenReturn(CategoryData.CATEGORIASDTO.get(i));
        }

        List<CategoryDTO> categories = service.getCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(categories.get(0), CategoryData.CATEGORIASDTO.get(0));
        assertEquals(categories.get(1), CategoryData.CATEGORIASDTO.get(1));
    }

}

