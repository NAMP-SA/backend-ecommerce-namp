package com.namp.ecommerce.service.implementation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.namp.ecommerce.dto.SubcategoryDTO;
import com.namp.ecommerce.dto.SubcategoryWithProductsDTO;
import com.namp.ecommerce.mapper.MapperSubcategory;
import com.namp.ecommerce.model.Subcategory;
import com.namp.ecommerce.repository.ISubcategoryDAO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SubcategoryTest {

    @Mock
    ISubcategoryDAO subcategoryDAO;

    @Mock
    MapperSubcategory mapperSubcategory;

    @InjectMocks
    SubcategoryImplementation subcategoryImplementation; 

    private SubcategoryImplementation spySubcategoryImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spySubcategoryImplementation = spy(subcategoryImplementation);
    }
    
    @Test
    void findSubcategories(){
        when(subcategoryDAO.findAll()).thenReturn(SubcategoryData.SUBCATEGORIES);

        for (int i = 0; (i < SubcategoryData.SUBCATEGORIES.size()); i++){
            when(mapperSubcategory.convertSubcategoryToDto(SubcategoryData.SUBCATEGORIES.get(i))).thenReturn(SubcategoryData.SUBCATEGORIESDTO.get(i));
        }

        List<SubcategoryDTO> result = subcategoryImplementation.getSubcategories();

        assertNotNull(result);
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.size(), result.size());
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.get(0), result.get(0)); 
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.get(1), result.get(1));   
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.get(2), result.get(2));   
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.get(3), result.get(3));   
    }

    @Test
    void findByIdSubcategory(){
        when(subcategoryDAO.findByIdSubcategory(1L)).thenReturn(SubcategoryData.SUBCATEGORIES.get(0));
        when(mapperSubcategory.convertSubcategoryToDto(SubcategoryData.SUBCATEGORIES.get(0)))
                .thenReturn(SubcategoryData.SUBCATEGORIESDTO.get(0));
    
       
        SubcategoryDTO subcategoryDTO = subcategoryImplementation.findById(1L);
    
        assertNotNull(subcategoryDTO);
        assertEquals(1L, subcategoryDTO.getIdSubcategory());
        assertEquals("Cervezas", subcategoryDTO.getName());
    }

    @Test
    void findSubcategoriesWithProducts(){
        when(subcategoryDAO.findAll()).thenReturn(SubcategoryData.SUBCATEGORIES);

        for (int i = 0; (i < SubcategoryData.SUBCATEGORIES.size()); i++) {
            when(mapperSubcategory.convertSubcategoryWithProductsToDto(SubcategoryData.SUBCATEGORIES.get(i)))
                .thenReturn(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(i));
        }

        List<SubcategoryWithProductsDTO> subcategoryWithProductsDTO = subcategoryImplementation.getSubcategoriesWithProducts();

        assertNotNull(subcategoryWithProductsDTO);
        assertEquals(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.size(), subcategoryWithProductsDTO.size());
        assertEquals(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(0), subcategoryWithProductsDTO.get(0));
        assertEquals(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(1), subcategoryWithProductsDTO.get(1));
        assertEquals(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(2), subcategoryWithProductsDTO.get(2));
    }

    @Test
    void findSubcategoriesIdWhitProducts(){
        when(subcategoryDAO.findByIdSubcategory(1L)).thenReturn(SubcategoryData.SUBCATEGORIES.get(0));
        when(mapperSubcategory.convertSubcategoryIdWithProductsToDto(SubcategoryData.SUBCATEGORIES.get(0)))
            .thenReturn(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(0));
    
        SubcategoryWithProductsDTO subcategoryWithProductsDTO = subcategoryImplementation.getSubcategoriesIdWithProducts(1L);
    
        assertNotNull(subcategoryWithProductsDTO);
        assertEquals(SubcategoryData.SUBCATEGORIESWITHPRODUCTSDTO.get(0), subcategoryWithProductsDTO);
    }

    @Test
    void saveValidSubcategory(){
        SubcategoryDTO subcategoryDTO = SubcategoryData.SUBCATEGORIESDTO.get(0);
        String normalizedName = subcategoryDTO.getName().replaceAll("\\s+", "").trim().toUpperCase();

        when(spySubcategoryImplementation.verifyName(normalizedName)).thenReturn(false); 
        when(mapperSubcategory.convertDtoToSubcategory(subcategoryDTO)).thenReturn(SubcategoryData.SUBCATEGORIES.get(0));
        when(subcategoryDAO.save(SubcategoryData.SUBCATEGORIES.get(0))).thenReturn(SubcategoryData.SUBCATEGORIES.get(0));
        when(mapperSubcategory.convertSubcategoryToDto(SubcategoryData.SUBCATEGORIES.get(0))).thenReturn(subcategoryDTO);

        SubcategoryDTO subcategoryDTO2 = spySubcategoryImplementation.save(subcategoryDTO);
        
        assertNotNull(subcategoryDTO2);
        assertEquals(subcategoryDTO, subcategoryDTO2);
    }

    @Test
    void updateSubcategory(){
        SubcategoryDTO existinSubcategoryDTO = SubcategoryData.SUBCATEGORIESDTO.get(0);
        // Datos nuevos
        Subcategory newSubcategory = SubcategoryData.SUBCATEGORIES.get(1); 
        Subcategory updatedSubcategory = SubcategoryData.SUBCATEGORIES.get(1);

        String normalizedName = newSubcategory.getName().replaceAll("\\s+", " ").trim().toUpperCase();

        when(subcategoryDAO.findByIdSubcategory(existinSubcategoryDTO.getIdSubcategory()))
            .thenReturn(SubcategoryData.SUBCATEGORIES.get(0));
        when(spySubcategoryImplementation.verifyName(normalizedName, existinSubcategoryDTO.getIdSubcategory())).thenReturn(false);
        when(subcategoryDAO.save(any(Subcategory.class))).thenReturn(updatedSubcategory);
        when(mapperSubcategory.convertSubcategoryToDto(updatedSubcategory)).thenReturn(SubcategoryData.SUBCATEGORIESDTO.get(1));

        SubcategoryDTO result = spySubcategoryImplementation.update(existinSubcategoryDTO, newSubcategory);

        assertNotNull(result);
        assertEquals(SubcategoryData.SUBCATEGORIESDTO.get(1), result);
    }
    

    @Test
    void deleteSubcategory(){
        SubcategoryDTO subcategoryDTO = SubcategoryData.SUBCATEGORIESDTO.get(0);
        Subcategory subcategory = SubcategoryData.SUBCATEGORIES.get(0);

        when(subcategoryDAO.findByIdSubcategory(subcategoryDTO.getIdSubcategory())).thenReturn(subcategory);

        assertDoesNotThrow(() -> spySubcategoryImplementation.delete(subcategoryDTO));
        verify(subcategoryDAO).delete(subcategory);
    }

    
}