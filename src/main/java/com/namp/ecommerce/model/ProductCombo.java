package com.namp.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ProductCombo")
public class ProductCombo implements Serializable{
    @Id
    @Column(name = "idProductCombo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProductCombo;  

    @NotNull(message = "La cantidad no debe de estar vacia")
    @Min(value = 0, message = "El valor debe ser un número positivo")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "fk_product", referencedColumnName = "idProduct")
    private Product idProduct;

    @ManyToOne
    @JoinColumn(name = "fk_combo", referencedColumnName = "idCombo")
    private Combo idCombo;

}


