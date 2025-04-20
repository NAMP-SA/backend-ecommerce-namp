package com.namp.ecommerce.service;

import java.util.List;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.model.DiscountCoupon;

public interface IDiscountCouponService {

    List<DiscountCouponDTO> getDiscountCoupons();

    DiscountCouponDTO save(DiscountCouponDTO discountCouponDTO);

    DiscountCouponDTO update(DiscountCouponDTO discountCouponDTO, DiscountCoupon discountCoupon);

    void delete(DiscountCouponDTO discountCouponDTO);

    DiscountCouponDTO findById(long id);

    boolean verifyName(String normalizedName);

    boolean verifyName(String normalizedName, long categoryId);

    String generarCodigoAleatorio();
}
