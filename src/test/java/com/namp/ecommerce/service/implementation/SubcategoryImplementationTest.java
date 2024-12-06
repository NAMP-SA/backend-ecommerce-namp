package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.model.Subcategory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SubcategoryImplementationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubcategoryImplementation subcategoryService;

    @InjectMocks
    private SubcategoryImplementation subcategoryServiceLogic;

    @Autowired
    private Validator validator;

    @Test
    public void testName() throws Exception{

        SubcategoryDTO subcategoryDTO = new SubcategoryDTO( 1, "NombreSubcategoria", "DescripcionSubCategoria", null);

        // Simula que cuando se llame a save con cualquier CategoryDTO, se devolverá categoryDTO
        when(subcategoryService.save(Mockito.any(SubcategoryDTO.class))).thenReturn(subcategoryDTO);

        String validSubcategoryJson = """
        {
            "idSubcategory": 1,
            "name": "NombreSubcategoria",
            "description": "DescripcionSubCategoria",
            "idCategory": null
        }
        """;

        mockMvc.perform(post("/api-namp/subcategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validSubcategoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testName_Limit(){

        Subcategory subcategory = new Subcategory();
        subcategory.setName("Bebidas fermentadas no destiladas a base de frutas y cereales con ligeros toques mentolados");


        Set<ConstraintViolation<Subcategory>> violations = validator.validateProperty(subcategory, "name");

        assertEquals(1, violations.size(), "El nombre no puede tener más de 50 caracteres");

    }

    @Test
    public void testName_Empty(){

        Subcategory subcategory = new Subcategory();
        subcategory.setName(" ");


        Set<ConstraintViolation<Subcategory>> violations = validator.validateProperty(subcategory, "name");

        assertEquals(2, violations.size(), "El nombre no puede estar vacío ni contener solo espacios");

    }


}

/*
 LISTADO DE NUEVOS CAMBIOS
    1. @NotBlank -> name para que no acepte ninguna cadena con espacios.

*/