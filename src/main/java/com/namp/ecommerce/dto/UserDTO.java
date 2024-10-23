package com.namp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}