package com.namp.ecommerce.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
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
@Table(name = "RegisterStock")
public class RegisterStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRegisterStock")
    private long idRegisterStock;

  
    private Timestamp dateTime;

    @NotNull(message = "La cantidad no puede estar vacia")
    @Min(value = 0, message = "El valor debe ser un número positivo")
    private int quantity; 

    @NotNull(message = "El producto no puede estar vacio")
    @ManyToOne
    @JoinColumn(name="fk_product",referencedColumnName = "idProduct")
    private Product idProduct; 
}
