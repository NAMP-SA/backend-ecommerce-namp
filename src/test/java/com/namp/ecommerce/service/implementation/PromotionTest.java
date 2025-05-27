package com.namp.ecommerce.service.implementation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PromotionTest {

    @Mock
    IPromotionDAO repository;

    @Mock
    private MockMvc mockMvc;

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

    @Test
    void save_ThrowsException_WhenEndDateBeforeStartDate() {
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setName("Promo error");
        promotionDTO.setDateTimeStart(Timestamp.valueOf("2025-06-01 10:00:00"));
        promotionDTO.setDateTimeEnd(Timestamp.valueOf("2025-05-29 10:00:00"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.save(promotionDTO)
        );

        assertEquals("The end date must be later than the start date", exception.getMessage());
    }

    @Test
    void save_ThrowsException_WhenStartDateInThePast() {
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setName("Promo antigua");
        promotionDTO.setDateTimeStart(Timestamp.valueOf("2020-01-01 10:00:00"));
        promotionDTO.setDateTimeEnd(Timestamp.valueOf("2025-07-07 10:00:00"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.save(promotionDTO)
        );

        assertEquals("The start date must be later than the current date", exception.getMessage());
    }

}
