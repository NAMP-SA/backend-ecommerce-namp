package com.namp.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IStatisticService {

    List<Map<String, Object>> getDailyIncome(LocalDateTime startDate, LocalDateTime endDate);

    List<Map<String, Object>> getMonthlyIncome(Integer year);

    List<Map<String, Object>> getTopProductSold(Integer limit, LocalDateTime startDate, LocalDateTime endDate);

    List<Map<String, Object>> getTopProductSold(Integer limit);

    List<Map<String, Object>> getTopComboSold(Integer limit, LocalDateTime startDate, LocalDateTime endDate);

    List<Map<String, Object>> getTopComboSold(Integer limit);

    List<Map<String, Object>> getStockByPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
