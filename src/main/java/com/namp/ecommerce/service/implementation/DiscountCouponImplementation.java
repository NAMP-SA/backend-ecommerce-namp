package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.model.DiscountCoupon;
import com.namp.ecommerce.repository.IDiscountCouponDAO;
import com.namp.ecommerce.service.IDiscountCouponService;

@Service
public class DiscountCouponImplementation implements IDiscountCouponService {

    @Autowired
    private IDiscountCouponDAO couponDAO;

    @Override
    public List<DiscountCoupon> getDiscountCoupons() {
        return couponDAO.findAll();
    }

    @Override
    public DiscountCouponDTO save(DiscountCouponDTO discountCouponDTO) {
        String normalizedCode = discountCouponDTO.getCodigo()..replaceAll("\\s+", " ").trim().toUpperCase();
        
        if(!verifyName(normalizedCode)){
            disco
        }
    }

    @Override
    public DiscountCouponDTO update(DiscountCouponDTO discountCouponDTO, DiscountCoupon discountCoupon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(DiscountCouponDTO discountCouponDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public DiscountCouponDTO findById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public boolean verifyName(String normalizedName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyName'");
    }

    @Override
    public boolean verifyName(String normalizedName, long categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyName'");
    }

}
