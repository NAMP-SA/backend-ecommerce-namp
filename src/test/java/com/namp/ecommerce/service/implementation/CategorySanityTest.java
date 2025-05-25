package com.namp.ecommerce.service.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.service.ICategoryService;

import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CategorySanityTest {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void testCreateCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("Test Description");

        CategoryDTO savedCategory = categoryService.save(categoryDTO);

        assertNotNull(savedCategory, "The category should not be null");
        assertNotNull(savedCategory.getIdCategory(), "\r\n" + " The ID of the category should be assigned to");
        assertEquals("TEST CATEGORY", savedCategory.getName(), "The name should be normalized to uppercase");
        assertEquals("Test Description", savedCategory.getDescription(), "The description should be remain teh same");

        CategoryDTO retrivedCategory = categoryService.findById(savedCategory.getIdCategory());
        assertNotNull(retrivedCategory, "Should be able to retireve the created category");
        assertEquals(savedCategory.getIdCategory(), retrivedCategory.getIdCategory(), "The ID should be the same");
        assertEquals(savedCategory.getName(), retrivedCategory.getName(), "The name should be teh same");
    }
}
