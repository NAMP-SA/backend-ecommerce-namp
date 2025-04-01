package com.namp.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namp.ecommerce.model.DiscountCoupon;

public interface IDiscountCouponDAO extends CrudRepository<DiscountCoupon, Long> {
    List<DiscountCoupon> findAll();
}
