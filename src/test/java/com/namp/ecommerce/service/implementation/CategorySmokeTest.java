package com.namp.ecommerce.service.implementation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.model.Category;
import com.namp.ecommerce.service.ICategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategorySmokeTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService categoryService;

    private String BASE_URL = "/api-namp";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCategories__returns200() throws Exception {
        when(categoryService.getCategories()).thenReturn(CategoryData.CATEGORIASDTO);

        mockMvc.perform(get(BASE_URL + "/category"))
                .andExpect(status().isOk());
    }

    @Test
    void getCategoriesWithSubcategories__returns200() throws Exception {
        when(categoryService.getCategoriesWithSubcategories()).thenReturn(CategoryData.CATEGORIAS_SUBCATEGORIAS);

        mockMvc.perform(get(BASE_URL + "/categoryWithSubcategories"))
                .andExpect(status().isOk());
    }

    @Test
    void getCategoryWithSubcategoriesById__returns200() throws Exception {
        when(categoryService.getCategoriesIdWithSubcategories(1L)).thenReturn(CategoryData.CATEGORIA_SUBCATEGORIAS);

        mockMvc.perform(get(BASE_URL + "/categoryWithSubcategories/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory__returns200() throws Exception {
        CategoryDTO dto = CategoryData.CATEGORIASDTO.get(0);
        when(categoryService.save(any(CategoryDTO.class))).thenReturn(dto);

        mockMvc.perform(post(BASE_URL + "/admin/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCategory__returns200() throws Exception {
        CategoryDTO dto = CategoryData.CATEGORIASDTO.get(0);

        when(categoryService.findById(1L)).thenReturn(dto);

        doNothing().when(categoryService).delete(dto);

        mockMvc.perform(delete(BASE_URL + "/admin/category/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategory__returns200() throws Exception {
        CategoryDTO dto = CategoryData.CATEGORIASDTO.get(0);
        Category updated = CategoryData.CATEGORIAS.get(1);
        CategoryDTO updatedDto = CategoryData.CATEGORIASDTO.get(1);

        when(categoryService.findById(1L)).thenReturn(dto);
        when(categoryService.update(eq(dto), any(Category.class))).thenReturn(updatedDto);

        mockMvc.perform(put(BASE_URL + "/admin/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }
}
