package com.namp.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.model.DiscountCoupon;

@Component
public class MapperDiscountCoupon {
    @Autowired
    private MapperUtil mapperUtil;

    public DiscountCoupon convertDtoToDiscountCoupon(DiscountCouponDTO discountCouponDTO) {
        DiscountCoupon discountCoupon = new DiscountCoupon();

        discountCoupon.setCode(discountCouponDTO.getCode());
        discountCoupon.setDiscount(discountCouponDTO.getDiscount());
        discountCoupon.setCurrent(discountCouponDTO.isCurrent());

        return discountCoupon;
    }

    public DiscountCouponDTO convertDiscountCouponToDTO(DiscountCoupon discountCoupon) {
        DiscountCouponDTO discountCouponDTO = mapperUtil.convertDiscountCouponToDto(discountCoupon);
        return discountCouponDTO;
    }
}
