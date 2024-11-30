package com.namp.ecommerce.repository;

import com.namp.ecommerce.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

public interface IStatisticDAO extends CrudRepository<Order, Long> {

    @Query(value = """
            SELECT
                DATE(o.date_time) AS dayName,
                SUM(od.sub_total) AS daily_Income
            FROM
                "order" o
            JOIN
                order_detail od ON o.id_order = od.fk_order
            WHERE
                o.date_time BETWEEN :'startDate'::TIMESTAMP AND :'endDate'::TIMESTAMP AND o.fk_state = 2
            GROUP BY
                DATE(o.date_time)
            ORDER BY
                DATE(o.date_time);
            """, nativeQuery = true)
    List<Map<String, Object>> getDailyIncome(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            SELECT
                EXTRACT(MONTH FROM o.date_time) AS monthName,
                SUM(od.sub_total) AS monthly_income
            FROM
                "order" o
            JOIN
                order_detail od ON o.id_order = od.fk_order
            WHERE
                EXTRACT(YEAR FROM o.date_time) = :'year' AND o.fk_state = 2
            GROUP BY    
                EXTRACT(MONTH FROM o.date_time)
            ORDER BY
                monthName;
            """, nativeQuery = true)
    List<Map<String, Object>> getMonthlyIncome(@Param("year") Integer year);


    @Query(value = """
            SELECT
                p.name AS product,
                SUM(od.quantity) AS total_sold
            FROM
                order_detail od
            JOIN
                product p ON od.fk_product = p.id_product
            JOIN 
                "order" o ON od.fk_order = o.id_order
            WHERE
                o.date_time BETWEEN :'startDate'::TIMESTAMP AND :'endDate'::TIMESTAMP AND o.fk_state = 2
            GROUP BY
                p.name
            ORDER BY
                total_vendido DESC
            LIMIT :'limit';
    """,nativeQuery = true)
    List<Map<String, Object>> getTopProductSold(@Param("limit") Integer limit,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            SELECT
                p.name AS product,
                SUM(od.quantity) AS total_sold
            FROM
                order_detail od
            JOIN
                product p ON od.fk_product = p.id_product
            JOIN
                "order" o ON od.fk_order = o.id_order
            WHERE
                o.fk_state = 2
            GROUP BY
                p.name
            ORDER BY
                total_vendido DESC
            LIMIT :'limit';
    """,nativeQuery = true)
    List<Map<String, Object>> getTopProductSold(@Param("limit") Integer limit);

    @Query(value = """
            SELECT
                c.name AS combo,
                SUM(od.quantity) AS total_sold
            FROM
                order_detail od
            JOIN
                combo c ON od.fk_combo = c.id_combo
            JOIN
                "order" o ON od.fk_order = o.id_order
            WHERE
                o.date_time BETWEEN :'startDate'::TIMESTAMP AND :'endDate'::TIMESTAMP AND o.fk_state = 2
            GROUP BY
                c.name
            ORDER BY
                total_vendido DESC
            LIMIT :'limit';
    """,nativeQuery = true)
    List<Map<String, Object>> getTopComboSold(@Param("limit") Integer limit,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            SELECT
                c.name AS combo,
                SUM(od.quantity) AS total_vendido
            FROM
                order_detail od
            JOIN
                combo c ON od.fk_combo = c.id_combo
            JOIN
                "order" o ON od.fk_order = o.id_order
            WHERE
                o.fk_state = 2
            GROUP BY
                c.name
            ORDER BY
                total_vendido DESC
            LIMIT :'limit';
    """,nativeQuery = true)
    List<Map<String, Object>> getTopComboSold(@Param("limit") Integer limit);

    @Query(value = """
            SELECT
                p.name AS productName,
                SUM(rs.quantity) AS total_stock
            FROM
                register_stock rs
            JOIN
                product p ON rs.fk_product = p.id_product
            WHERE
                rs.date_time BETWEEN :'startDate'::TIMESTAMP AND :'endDate'::TIMESTAMP
            GROUP BY
                p.name
            ORDER BY
                p.name;
    """,nativeQuery = true)
    List<Map<String,Object>> getStockByPeriod(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);
}

