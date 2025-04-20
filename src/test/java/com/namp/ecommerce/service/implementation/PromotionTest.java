package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.mapper.MapperPromotion;
import com.namp.ecommerce.repository.IPromotionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromotionTest {

    @Mock
    IPromotionDAO repository;

    @Mock
    MapperPromotion mapperPromotion;

    @InjectMocks
    PromotionImplementation service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findPromotionById() {
        when(repository.findByIdPromotion(1L)).thenReturn(PromotionData.PROMOTIONS.get(0));
        when(mapperPromotion.convertPromotionToDto(PromotionData.PROMOTIONS.get(0))).thenReturn(PromotionData.PROMOTIONSDTO.get(0));

        PromotionDTO promotion = service.findById(1L);

        assertNotNull(promotion);
        assertEquals(1L, promotion.getIdPromotion());
        assertEquals("Promocion 1", promotion.getName());
    }

    @Test
    void findPromotionsWithProducts() {
        when(repository.findAll()).thenReturn(PromotionData.PROMOTIONS);

        for (int i = 0; i < (PromotionData.PROMOTIONS.size()); i++){
            when(mapperPromotion.convertPromotionWithProductsToDto(PromotionData.PROMOTIONS.get(i))).thenReturn(PromotionData.PROMOTIONSWITHPRODUCTSDTO.get(i));
        }

        List<PromotionWithProductsDTO> promotions = service.getPromotionsWithProducts();

        assertNotNull(promotions);
        assertEquals(2, promotions.size());
        assertEquals(promotions.get(0), PromotionData.PROMOTIONSWITHPRODUCTSDTO.get(0));
        assertEquals(promotions.get(1), PromotionData.PROMOTIONSWITHPRODUCTSDTO.get(1));
    }

    @Test
    void savePromotion() {
        when(mapperPromotion.convertPromotionToDto(PromotionData.PROMOTIONS.get(0))).thenReturn(PromotionData.PROMOTIONSDTO.get(0));
        when(mapperPromotion.convertDtoToPromotion(PromotionData.PROMOTIONSDTO.get(0))).thenReturn(PromotionData.PROMOTIONS.get(0));
        when(repository.save(PromotionData.PROMOTIONS.get(0))).thenReturn(PromotionData.PROMOTIONS.get(0));

        PromotionDTO promotionDTO = PromotionData.PROMOTIONSDTO.get(0);

        PromotionDTO promotionSaved = service.save(promotionDTO);

        assertNotNull(promotionSaved);
        assertEquals("PROMOCION 1", promotionSaved.getName());
        assertEquals(50, promotionSaved.getDiscount());
        assertEquals(PromotionData.PROMOTIONS.get(0).getDateTimeStart(), promotionSaved.getDateTimeStart());

    }

}
