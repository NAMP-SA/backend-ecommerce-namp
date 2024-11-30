package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.repository.IStatisticDAO;
import com.namp.ecommerce.service.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StatisticImplementation implements IStatisticService {

    @Autowired
    private IStatisticDAO statisticDAO;

    @Override
    public List<Map<String, Object>> getDailyIncome(LocalDateTime startDate, LocalDateTime endDate) {
        return statisticDAO.getDailyIncome(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getMonthlyIncome(Integer year) {
        return statisticDAO.getMonthlyIncome(year);
    }

    @Override
    public List<Map<String, Object>> getTopProductSold(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        return statisticDAO.getTopProductSold(limit, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getTopProductSold(Integer limit) {
        return statisticDAO.getTopProductSold(limit);
    }

    @Override
    public List<Map<String, Object>> getTopComboSold(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        return statisticDAO.getTopComboSold(limit, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getTopComboSold(Integer limit) {
        return statisticDAO.getTopComboSold(limit);
    }

    @Override
    public List<Map<String, Object>> getStockByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return statisticDAO.getStockByPeriod(startDate, endDate);
    }



}
