package com.namp.ecommerce.service.implementation;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.mapper.MapperDiscountCoupon;
import com.namp.ecommerce.model.DiscountCoupon;
import com.namp.ecommerce.repository.IDiscountCouponDAO;
import com.namp.ecommerce.service.IDiscountCouponService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountCouponImplementation implements IDiscountCouponService {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    private IDiscountCouponDAO discountCouponDAO;

    @Autowired
    private MapperDiscountCoupon mapperDiscountCoupon;

    @Override
    public List<DiscountCouponDTO> getDiscountCoupons() {
        return discountCouponDAO.findAll()
                .stream()
                .map(mapperDiscountCoupon::convertDiscountCouponToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountCouponDTO save(DiscountCouponDTO discountCouponDTO) {
        String generatedCode;
        String normalizedCode;

        do {
            generatedCode = this.generarCodigoAleatorio();
            normalizedCode = generatedCode.replaceAll("\\s+", " ").trim().toUpperCase();

        } while (verifyName(normalizedCode));

            discountCouponDTO.setCode(this.generarCodigoAleatorio());
            discountCouponDTO.setCurrent(true);
            DiscountCoupon discountCoupon = mapperDiscountCoupon.convertDtoToDiscountCoupon(discountCouponDTO);
            DiscountCoupon savedDiscountCoupon = discountCouponDAO.save(discountCoupon);
            return mapperDiscountCoupon.convertDiscountCouponToDTO(savedDiscountCoupon);
    }

    @Override
    public DiscountCouponDTO update(DiscountCouponDTO existingDiscountCouponDTO, DiscountCoupon discountCoupon) {
        DiscountCoupon existingDiscountCoupon = discountCouponDAO
                .findById(existingDiscountCouponDTO.getIdDiscountCoupon());

        if (existingDiscountCoupon == null) {
            return null;
        }
        existingDiscountCoupon.setDiscount(discountCoupon.getDiscount());
        existingDiscountCoupon.setCurrent(discountCoupon.isCurrent());

        DiscountCoupon updatedDiscountCoupon = discountCouponDAO.save(existingDiscountCoupon);

        return mapperDiscountCoupon.convertDiscountCouponToDTO(updatedDiscountCoupon);
    }

    @Override
    public void delete(DiscountCouponDTO discountCouponDTO) {
        DiscountCoupon discountCoupon = discountCouponDAO.findById(discountCouponDTO.getIdDiscountCoupon());
        if (discountCoupon == null) {
            throw new EntityNotFoundException(
                    "Discount Coupon not found with ID: " + discountCouponDTO.getIdDiscountCoupon());
        }
        discountCouponDAO.delete(discountCoupon);
    }

    @Override
    public DiscountCouponDTO findById(long id) {
        DiscountCoupon discountCoupon = discountCouponDAO.findById(id);
        if (discountCoupon == null) {
            return null;
        }
        return mapperDiscountCoupon.convertDiscountCouponToDTO(discountCoupon);
    }

    @Override
    public DiscountCouponDTO findByCode(String code) {
        DiscountCoupon discountCoupon = discountCouponDAO.findByCode(code);
        if (discountCoupon == null) {
            return null;
        }
        return mapperDiscountCoupon.convertDiscountCouponToDTO(discountCoupon);
    }

    @Override
    public boolean verifyName(String normalizedName) {
        List<DiscountCoupon> discountCoupons = discountCouponDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        // Comparar el nombre de la categoria que se quiere guardar, con todos los demas
        // sin espacio para ver si es el mismo
        for (DiscountCoupon discountCoupon : discountCoupons) {
            if (name.equals(discountCoupon.getCode().replaceAll("\\s+", ""))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verifyName(String normalizedName, long discountCouponId) {
        List<DiscountCoupon> discountCoupons = discountCouponDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        // Verifica si se repite el nombre en las demas categorías, menos con la que se
        // está actualizando
        for (DiscountCoupon discountCoupon : discountCoupons) {
            if (discountCoupon.getIdDiscountCoupon() != discountCouponId
                    && name.equals(discountCoupon.getCode().replaceAll("\s+", ""))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String generarCodigoAleatorio() {
        StringBuilder codigo = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }
        return codigo.toString();
    }

}
