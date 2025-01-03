package com.namp.ecommerce.auth;

import com.namp.ecommerce.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "El nombre no debe estar vacio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El nombre solo puede contener caracteres alfabeticos")
    private String name;

    @NotNull(message = "El apellido no debe estar vacio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El apellido solo puede contener caracteres alfabeticos")
    private String lastname;

    @NotBlank(message = "El email no puede estar vacío ni contener solo espacios")
    @NotNull(message = "El email no debe estar vacio")
    @Size(max = 50, message = "El email puede contener maximo 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El formate ingresado no es correcto, por favor intente nuevamente")
    private String email;

    @NotNull(message = "La direccion no debe estar vacia")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "La direccion solo puede contener caracteres alfanumericos")
    private String address;

    @NotNull(message = "El telefono no debe estar vacio")
    @Pattern(regexp = "^[0-9\\-\\+\\s]*$", message = "El telefono solo puede contener numeros y caracteres permitidos como - o +")
    private String phone;

    @NotBlank(message = "El username no puede estar vacío ni contener solo espacios")
    @NotNull(message = "El usuario no debe estar vacio")
    @Size(max = 20, message = "El username puede contener maximo 20 caracteres")
    @Pattern(regexp = "^(?!\\d+$)[a-zA-Z0-9 ]*$", message = "El username solo puede contener caracteres alfanumericos")
    private String username;

    @Size(min = 8, max = 24, message = "La password debe tener entre 8-24 caracteres")
    @NotNull(message = "La contrasena no debe estar vacia")
    String password;
    Role role; 
}
