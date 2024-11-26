package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.model.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CategoryImplementationTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CategoryImplementation categoryService;

    @InjectMocks
    private CategoryImplementation categoryServiceLogic;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDescription() throws Exception{

        // Crear el DTO que simula el objeto CategoryDTO que se va a devolver
        CategoryDTO categoryDTO = new CategoryDTO(1, "DescripcionCategoria1", "DescripcionCategoria1");

        // Simula que cuando se llame a save con cualquier CategoryDTO, se devolverá categoryDTO
        when(categoryService.save(Mockito.any(CategoryDTO.class))).thenReturn(categoryDTO);

        String validCategoryJson = """
    {
        "idCategory": 1,
        "name": "DescripcionCategoria1",
        "description": "DescripcionCategoria1"
    }
    """;

        mockMvc.perform(post("/api-namp/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCategoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDescription_Limit() throws Exception{

        Category category = new Category();
        category.setDescription("Famoso amaro italiano con notas intensas y amargas, ideal para mezclar con cola o disfrutar solo con hielo.");


        Set<ConstraintViolation<Category>> violations = validator.validateProperty(category, "description");

        assertEquals(1, violations.size(), "La descripcion puede contener maximo 100 caracteres");

    }

    @Test
    public void testDescription_Empty() throws Exception{

        Category category = new Category();
        category.setDescription("  ");


        Set<ConstraintViolation<Category>> violations = validator.validateProperty(category, "description");

        assertEquals(1, violations.size(), "La descripcion puede contener maximo 100 caracteres");

    }

    @Test public void registerCategory() throws Exception {

        String categoryJson = """
        {
            "name": "nombretres",
            "description": "DescripcionCategoria1"
        }
        """;

        CategoryDTO categoryDTO = new CategoryDTO(1, "nombretres", "DescripcionCategoria1");

        when(categoryService.save(Mockito.any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/api-namp/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("nombretres"));
    }

    @Test public void registerCategory_Exist() throws Exception {

        String categoryJson = """
        {
            "name": "nombreuno",
            "description": "DescripcionCategoria1"
        }
        """;

        CategoryDTO categoryDTO = new CategoryDTO(1, "nombreuno", "DescripcionCategoria1");

        when(categoryService.save(Mockito.any(CategoryDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api-namp/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isConflict());
    }

    @Test public void registerCategory_Empty() throws Exception {

        String categoryJson = """
        {
            "name": "",
            "description": "DescripcionCategoria1"
        }
        """;

        CategoryDTO categoryDTO = new CategoryDTO(1, "nombreuno", "DescripcionCategoria1");

        when(categoryService.save(Mockito.any(CategoryDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api-namp/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isConflict());
    }
}

/*
 LISTADO DE NUEVOS CAMBIOS
    @Size(max = 100, message = "La descripcion puede contener maximo 100 caracteres")
    @NotBlank(message = "La descripcion no puede estar vacío ni contener solo espacios")
 */

