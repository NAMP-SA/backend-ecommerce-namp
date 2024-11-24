package com.namp.ecommerce.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name="Promotion")
public class Promotion implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPromotion")
    private long idPromotion; 

    @NotNull(message = "El descuento no debe estar vacio")
    @Min(value = 0, message = "El descuento debe ser un número positivo")
    private double discount;

    @NotNull(message = "La fecha y hora no puede ser vacío")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp  dateTimeStart;
    
    @NotNull(message = "La fecha y hora no puede ser vacío")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp  dateTimeEnd;

    private boolean inEffect; 

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_discount_type", referencedColumnName = "idDiscountType")
    private DiscountType idDiscountType; 

    @OneToMany(mappedBy = "idPromotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
    
}
