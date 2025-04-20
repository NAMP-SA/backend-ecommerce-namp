package com.namp.ecommerce.service.implementation;


import com.namp.ecommerce.dto.PromotionDTO;
import com.namp.ecommerce.dto.PromotionWithProductsDTO;
import com.namp.ecommerce.model.Promotion;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromotionData {

    private static Timestamp dateTimeStart1 = Timestamp.valueOf("2025-05-10 08:30:00");
    private static Timestamp dateTimeEnd1 = Timestamp.valueOf("2025-12-30 08:30:00");

    private static Timestamp dateTimeStart2 = Timestamp.valueOf("2000-01-01 08:30:00");
    private static Timestamp dateTimeEnd2 = Timestamp.valueOf("2000-01-01 10:30:00");

    public final static List<Promotion> PROMOTIONS = Arrays.asList(
            new Promotion(1L, "Promocion 1", 50, dateTimeStart1, dateTimeEnd1, new ArrayList<>()),
            new Promotion(2L, "Promocion 2", 50, dateTimeStart2, dateTimeEnd2, new ArrayList<>())
    );


    public final static List<PromotionDTO> PROMOTIONSDTO = Arrays.asList(
            new PromotionDTO(1L, "Promocion 1", 50, dateTimeStart1, dateTimeEnd1),
            new PromotionDTO(2L, "Promocion 2", 50, dateTimeStart2, dateTimeEnd2)
    );

    public final static List<PromotionWithProductsDTO> PROMOTIONSWITHPRODUCTSDTO = Arrays.asList(
            new PromotionWithProductsDTO(1L, "Promocion 1", 50, dateTimeStart1, dateTimeEnd1, new ArrayList<>()),
            new PromotionWithProductsDTO(2L, "Promocion 2", 50, dateTimeStart2, dateTimeEnd2, new ArrayList<>())
    );
}
