package com.namp.ecommerce.service.implementation;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.DiscountCouponDTO;
import com.namp.ecommerce.dto.DiscountCouponEditRq;
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
        discountCouponDTO.setCodigo(this.generarCodigoAleatorio());
        discountCouponDTO.setVigente(true);
        String normalizedCode = discountCouponDTO.getCodigo().replaceAll("\\s+", " ").trim().toUpperCase();

        if (!verifyName(normalizedCode)) {
            DiscountCoupon discountCoupon = mapperDiscountCoupon.convertDtoToDiscountCoupon(discountCouponDTO);
            DiscountCoupon savedDiscountCoupon = discountCouponDAO.save(discountCoupon);
            return mapperDiscountCoupon.convertDiscountCouponToDTO(savedDiscountCoupon);
        }
        return null;
    }

    @Override
    public DiscountCouponDTO update(DiscountCouponDTO existingDiscountCouponDTO, DiscountCouponEditRq discountCoupon) {
        DiscountCoupon existingDiscountCoupon = discountCouponDAO
                .findById(existingDiscountCouponDTO.getIdDiscountCoupon());

        if (existingDiscountCoupon == null || !existingDiscountCoupon.isVigente()) {
            return null;
        }
        existingDiscountCoupon.setDescuento(discountCoupon.getDescuento());
        existingDiscountCoupon.setVigente(discountCoupon.isVigente());

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
    public boolean verifyName(String normalizedName) {
        List<DiscountCoupon> discountCoupons = discountCouponDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        // Comparar el nombre de la categoria que se quiere guardar, con todos los demas
        // sin espacio para ver si es el mismo
        for (DiscountCoupon discountCoupon : discountCoupons) {
            if (name.equals(discountCoupon.getCodigo().replaceAll("\\s+", ""))) {
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
                    && name.equals(discountCoupon.getCodigo().replaceAll("\s+", ""))) {
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
