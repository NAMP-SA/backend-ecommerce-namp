package com.namp.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Subcategory")
public class Subcategory implements Serializable {
    @Id
    @Column(name = "idSubcategory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSubcategory;

    @NotBlank(message = "El nombre no puede estar vacío ni contener solo espacios")
    @NotNull(message = "El nombre no debe estar vacio")
    @Pattern(regexp = "^(?!\s*$)[a-zA-Z\s]+$",message = "El nombre debe contener solo caracteres alfabeticos")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotNull(message = "La descripcion no puede estar vacia")
    private String description;

    @NotNull(message = "La categoria no puede estar vacia")
    @ManyToOne
    @JoinColumn(name = "fk_category", referencedColumnName = "idCategory")
    private Category idCategory;
    
    @OneToMany(mappedBy = "idSubcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();




}
