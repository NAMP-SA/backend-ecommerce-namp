package com.namp.ecommerce.service.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.service.ISubcategoryService;

import static org.junit.jupiter.api.Assertions.*;

//Verificamos que la funcionalidad base del Createe funcione correctamente 
@SpringBootTest
@Transactional // El transactional es para que los cambios no se guarden en la base de datos 
public class SubcategorySanityTest {

    @Autowired
    private ISubcategoryService subcategoryService;


    @Test
    public void testCreateSubcategory() {
        
        //Creamos el DTO de la subcategoria 
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setName("Test Subcategory");
        subcategoryDTO.setDescription("Test Description");
        
        //Creamos el DTO de la categoria 
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(1L); 
        subcategoryDTO.setIdCategory(categoryDTO);
        
        //Ejecutamos el save de la subcategoria 
        SubcategoryDTO savedSubcategory = subcategoryService.save(subcategoryDTO);
        
        //Verificaciones necesarias para validar que se guardo correctamente la nueva subcategoria 
        assertNotNull(savedSubcategory, "The subcategory should not be null");
        assertNotNull(savedSubcategory.getIdSubcategory(), "\r\n" + " The ID of the subcategory should be assigned to");
        assertEquals("TEST SUBCATEGORY", savedSubcategory.getName(), 
                " The name should be normalized to uppercase");
        assertEquals("Test Description", savedSubcategory.getDescription(), 
                " The description should remain the same");
        

        //Verificamos que podamos rtornar el objto una vez creado
        SubcategoryDTO retrievedSubcategory = subcategoryService.findById(savedSubcategory.getIdSubcategory());
        assertNotNull(retrievedSubcategory, " Should be able to retrieve the created subcategory"); 
        assertEquals(savedSubcategory.getIdSubcategory(), retrievedSubcategory.getIdSubcategory(), 
                " The ID should be the same.");
        assertEquals(savedSubcategory.getName(), retrievedSubcategory.getName(), 
                " The name should be the same.");
    }
}