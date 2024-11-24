package com.namp.ecommerce.model;

import java.io.Serializable;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="DiscountType")
public class DiscountType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idDiscountType")
    private long idDiscountType;

    @NotNull(message = "El nombre no debe estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El nombre solo puede contener caracteres alfanuméricos")
    private String name;

    
    @OneToMany(mappedBy = "idPromotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Promotion> promotions = new ArrayList<>();
     



}
