package com.namp.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "OrderDetail")
public class OrderDetail implements Serializable{
    
    @Id  
    @Column(name="idDetailOrder")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDetailOrder;
    
    private double subTotal;

    @NotNull(message = "La cantidad no puede ser vacia")
    @Min(value = 0, message = "La cantidad debe ser un número positivo")
    private int quantity; 


    @ManyToOne
    @JoinColumn(name = "fk_product", referencedColumnName = "idProduct", nullable = true)
    private Product idProduct; 

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_order", referencedColumnName = "idOrder")
    private Order idOrder; 
  
  
    @ManyToOne
    @JoinColumn(name = "fk_combo", referencedColumnName = "idCombo", nullable = true)
    private Combo idCombo;
}

