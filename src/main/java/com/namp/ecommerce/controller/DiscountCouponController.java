package com.namp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.model.DiscountCoupon;
import com.namp.ecommerce.service.IDiscountCouponService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class DiscountCouponController {
    @Autowired
    private IDiscountCouponService discountCouponService;

    @GetMapping("/admin/coupon")
    public ResponseEntity<?> getCoupons() {
        try {
            return ResponseEntity.ok(discountCouponService.getDiscountCoupons());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the coupons:" + e.getMessage());
        }
    }

    @GetMapping("/admin/coupon/{id}")
    public ResponseEntity<?> getCoupon(@PathVariable long id) {
        try {
            if (discountCouponService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Coupon with ID " + id + " not found");
            }
            return ResponseEntity.ok(discountCouponService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the coupon " + e.getMessage());
        }
    }

    @GetMapping("/user/coupon/{code}")
    public ResponseEntity<?> getCouponByCode(@PathVariable String code) {
        try {

            if (code == null || code.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Coupon code must not be null or empty");
            }
            
            DiscountCouponDTO discountCoupon = discountCouponService.findByCode(code);
            if (discountCoupon == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Coupon with code " + code + " not found");
            }
            return ResponseEntity.ok(discountCoupon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the coupon " + e.getMessage());
        }
    }

    @PostMapping("/admin/coupon")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody DiscountCouponDTO discountCouponDTO) {
        try {
            DiscountCouponDTO createdDiscountCouponDTO = discountCouponService.save(discountCouponDTO);
            if (createdDiscountCouponDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This coupon already exists");
            }

            return ResponseEntity.ok(createdDiscountCouponDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the coupon:" + e.getMessage());
        }
    }

    @DeleteMapping("/admin/coupon/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable long id) {
        try {
            DiscountCouponDTO discountCouponDTO = discountCouponService.findById(id);

            if (discountCouponDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The coupon does not exist");
            }

            discountCouponService.delete(discountCouponDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the coupon: " + e.getMessage());
        }
    }

    @PutMapping("/admin/coupon/{id}")
    public ResponseEntity<?> updateCoupon(@PathVariable long id,
            @RequestBody DiscountCoupon discountCoupon) {
        try {
            DiscountCouponDTO existingDiscountCouponDTO = discountCouponService.findById(id);

            if (existingDiscountCouponDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The coupon does not exist");
            }

            DiscountCouponDTO updatedDiscountCouponDTO = discountCouponService.update(existingDiscountCouponDTO,
                    discountCoupon);

            if (updatedDiscountCouponDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entered code already exists");
            }

            return ResponseEntity.ok(updatedDiscountCouponDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the coupon: " + e.getMessage());
        }
    }

}
