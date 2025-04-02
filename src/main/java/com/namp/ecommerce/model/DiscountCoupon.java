package com.namp.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "DiscountCoupon")
public class DiscountCoupon {
    @Id
    @Column(name = "idDiscountCoupon")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDiscountCoupon;

    @NotEmpty(message = "El codigo no debe estar vacio")
    private String codigo;

    @NotNull(message = "El descuento no puede estar vacio")
    private int descuento;

    private boolean vigente;

    @OneToOne(mappedBy = "discountCoupon")
    private Order order;
}
