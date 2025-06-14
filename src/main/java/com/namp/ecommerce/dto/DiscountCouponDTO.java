package com.namp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCouponDTO {
    private long idDiscountCoupon;
    private String code;
    private int discount;
    private boolean current;
}
