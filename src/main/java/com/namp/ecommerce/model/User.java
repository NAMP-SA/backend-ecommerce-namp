package com.namp.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name="users")
public class User implements Serializable {

    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @NotNull(message = "El nombre no debe estar vacio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El nombre solo puede contener caracteres alfabeticos")
    private String name;

    @NotNull(message = "El apellido no debe estar vacio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El apellido solo puede contener caracteres alfabeticos")
    private String lastname;

    @NotNull(message = "El email no debe estar vacio")
    @Size(max = 50, message = "El email puede contener maximo 50 caracteres")
    private String email;

    @NotNull(message = "La direccion no debe estar vacia")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "La direccion solo puede contener caracteres alfanumericos")
    private String address;

    @NotNull(message = "El telefono no debe estar vacio")
    @Pattern(regexp = "^[0-9\\-\\+\\s]*$", message = "El telefono solo puede contener numeros y caracteres permitidos como - o +")
    private String phone;

    @NotNull(message = "El usuario no debe estar vacio")
    @Size(max = 20, message = "El email puede contener maximo 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "La direccion solo puede contener caracteres alfanumericos")
    private String username;

    @NotNull(message = "La contrasena no debe estar vacia")
    private String password;


}
