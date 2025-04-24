package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.CategoryWithSubcategoriesDTO;
import com.namp.ecommerce.mapper.MapperCategory;
import com.namp.ecommerce.model.Category;

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
        when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIAS.get(0)))
                .thenReturn(CategoryData.CATEGORIASDTO.get(0));

        CategoryDTO category = service.findById(1L);

        assertNotNull(category);
        assertEquals(1L, category.getIdCategory());
        assertEquals("Bebidas con alcohol", category.getName());
    }

    @Test
    void findCategories() {
        when(repository.findAll()).thenReturn(CategoryData.CATEGORIAS);

        for (int i = 0; i < (CategoryData.CATEGORIAS.size()); i++) {
            when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIAS.get(i)))
                    .thenReturn(CategoryData.CATEGORIASDTO.get(i));
        }

        List<CategoryDTO> categories = service.getCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(categories.get(0), CategoryData.CATEGORIASDTO.get(0));
        assertEquals(categories.get(1), CategoryData.CATEGORIASDTO.get(1));
    }

    @Test
    void findCategorYWithSubcategories() {
        when(repository.findByIdCategory(1L)).thenReturn(CategoryData.CATEGORIAS.get(0));
        when(mapperCategory.convertCategoryIdWithSubcategoryToDto(CategoryData.CATEGORIAS.get(0)))
                .thenReturn(CategoryData.CATEGORIA_SUBCATEGORIAS);

        CategoryWithSubcategoriesDTO categoryWithSubcategoriesDTO = service.getCategoriesIdWithSubcategories(1L);

        assertNotNull(categoryWithSubcategoriesDTO);
        assertEquals(CategoryData.CATEGORIA_SUBCATEGORIAS, categoryWithSubcategoriesDTO);
        assertEquals(CategoryData.CATEGORIA_SUBCATEGORIAS.getSubcategories(),
                categoryWithSubcategoriesDTO.getSubcategories());

    }

    @Test
    void findCategoriesWithSubcategories() {
        when(repository.findAll()).thenReturn(CategoryData.CATEGORIAS);
        for (int i = 0; i < (CategoryData.CATEGORIAS.size()); i++) {
            when(mapperCategory.convertCategoryWithSubcategoryToDto(CategoryData.CATEGORIAS.get(i)))
                    .thenReturn(CategoryData.CATEGORIAS_SUBCATEGORIAS.get(i));
        }

        List<CategoryWithSubcategoriesDTO> categoriesWithSubcategoriesDTO = service.getCategoriesWithSubcategories();

        assertNotNull(categoriesWithSubcategoriesDTO);
        assertEquals(CategoryData.CATEGORIAS_SUBCATEGORIAS.size(), categoriesWithSubcategoriesDTO.size());
        assertEquals(CategoryData.CATEGORIAS_SUBCATEGORIAS.get(0), categoriesWithSubcategoriesDTO.get(0));
        assertEquals(CategoryData.CATEGORIAS_SUBCATEGORIAS.get(1), categoriesWithSubcategoriesDTO.get(1));

    }

    @Test
    void saveCategory() {
        CategoryDTO newCategoryDTO = CategoryData.CATEGORIADTO;
        when(repository.save(any())).thenReturn(CategoryData.CATEGORIA);
        when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIA))
                .thenReturn(CategoryData.CATEGORIADTO);

        CategoryDTO categoryDTO = service.save(newCategoryDTO);
        assertNotNull(categoryDTO.getIdCategory());
        assertEquals(1L, categoryDTO.getIdCategory());
        assertEquals("BEBIDAS ALCOHOLICAS", categoryDTO.getName());
    }

    @Test
    void updateCategory() {
        CategoryDTO newCategoryDTO = CategoryData.CATEGORIADTO;
        Category existing_category = CategoryData.CATEGORIA;

        when(repository.save(any())).thenReturn(CategoryData.CATEGORIA_ACTUALIZADA);
        when(repository.findByIdCategory(existing_category.getIdCategory())).thenReturn(CategoryData.CATEGORIA);

        when(mapperCategory.convertCategoryToDto(CategoryData.CATEGORIA_ACTUALIZADA))
                .thenReturn(CategoryData.CATEGORIADTO_ACTUALIZADA);

        CategoryDTO categoryDTO = service.update(newCategoryDTO, existing_category);

        assertNotNull(categoryDTO);
        assertEquals(CategoryData.CATEGORIA_ACTUALIZADA.getIdCategory(), categoryDTO.getIdCategory());
        assertEquals(CategoryData.CATEGORIA_ACTUALIZADA.getName(), categoryDTO.getName());

    }

}
