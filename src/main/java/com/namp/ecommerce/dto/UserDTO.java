package com.namp.ecommerce.dto;

import com.namp.ecommerce.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private long idUser;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private String confirmPassword;
    private Role role; 
}
