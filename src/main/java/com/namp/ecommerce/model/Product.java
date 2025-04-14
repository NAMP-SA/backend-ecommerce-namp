package com.namp.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="product")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduct")
    private long idProduct;

    @NotBlank(message = "El nombre no puede estar vacío ni contener solo espacios")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El nombre solo puede contener caracteres alfanuméricos")
    private String name;

    @NotNull(message = "La descripcion no debe estar vacia")
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    private String description;

    @NotNull(message = "El precio no debe estar vacio")
    @Min(value = 0, message = "El precio debe ser un número positivo")
    @Max(value = 500000, message = "El precio limite es de $500.000")
    private double price;

    @NotNull(message = "El stock no debe estar vacio")
    @Min(value = 0, message = "El stock debe ser un número positivo")
    @Max(value = 100000, message = "El stock limite es de 100.000 unidades")
    private int stock;

    @Value("${image.upload.dir}")
    private String img;
    //Falta promocion

    @OneToMany(mappedBy = "idProduct",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RegisterStock> registerStocks = new ArrayList<>();

    @Transient
    int simulatedStock;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_subcategory", referencedColumnName = "idSubcategory")
    private Subcategory idSubcategory;

    @OneToMany(mappedBy = "idProduct")
    private List<ProductCombo> productCombo = new ArrayList<>();

    @OneToMany(mappedBy = "idProduct")
    private List<OrderDetail> orderDetail = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "fk_promotion", referencedColumnName = "idPromotion", nullable = true)
    private Promotion idPromotion;
    
}
