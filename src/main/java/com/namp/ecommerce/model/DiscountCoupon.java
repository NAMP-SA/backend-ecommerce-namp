package com.namp.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 5, message = "El descuento debe de ser, al menos, del 5%")
    @Max(value = 20, message = "El descuento maximo es de 20%")
    private int descuento;

    private boolean vigente;

    @OneToMany(mappedBy = "idDiscountCoupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
