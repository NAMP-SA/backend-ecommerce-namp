package com.namp.ecommerce.controller;

import com.namp.ecommerce.service.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-namp")
public class StatisticController {

    @Autowired
    private IStatisticService statisticService;

    @GetMapping("/admin/getDailyIncome")
    ResponseEntity<?> statisticDailyIncome(@RequestParam LocalDateTime startDate,
                                           @RequestParam LocalDateTime endDate) {
        try {
            return ResponseEntity.ok(statisticService.getDailyIncome(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the daily income:" + e.getMessage());
        }
    }

    @GetMapping("/admin/getMonthlyIncome")
    ResponseEntity<?> statisticMonthlyIncome(@RequestParam Integer year) {
        try {
            return ResponseEntity.ok(statisticService.getMonthlyIncome(year));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the monthly income:" + e.getMessage());
        }
    }

    @GetMapping("/admin/getTopProductSold")
    ResponseEntity<?> statisticTopProductSold(@RequestParam Integer limit,
                                              @RequestParam(required = false) LocalDateTime startDate,
                                              @RequestParam(required = false) LocalDateTime endDate) {
        try {
            List<Map<String, Object>> results;

            if (startDate != null && endDate != null) {
                // Si se ingresan las fechas, llama al método correspondiente
                results = statisticService.getTopProductSold(limit, startDate, endDate);
            } else {
                // Si no se ingresan las fechas, usa el método predeterminado
                results = statisticService.getTopProductSold(limit);
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the top product sold:" + e.getMessage());
        }
    }

    @GetMapping("/admin/getTopComboSold")
    ResponseEntity<?> statisticTopComboSold(@RequestParam Integer limit,
                                            @RequestParam(required = false) LocalDateTime startDate,
                                            @RequestParam(required = false) LocalDateTime endDate) {
        try {
            List<Map<String, Object>> results;

            if (startDate != null && endDate != null) {
                // Si se ingresan las fechas, llama al método correspondiente
                results = statisticService.getTopComboSold(limit, startDate, endDate);
            } else {
                // Si no se ingresan las fechas, usa el método predeterminado
                results = statisticService.getTopComboSold(limit);
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the top product sold:" + e.getMessage());
        }
    }

    @GetMapping("/admin/getStockByPeriod")
    ResponseEntity<?> statisticStockByPeriod(@RequestParam LocalDateTime startDate,
                                             @RequestParam LocalDateTime endDate) {
        try {
            return ResponseEntity.ok(statisticService.getStockByPeriod(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the monthly income:" + e.getMessage());
        }
    }

}
