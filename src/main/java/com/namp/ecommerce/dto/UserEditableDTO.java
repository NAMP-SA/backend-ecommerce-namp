package com.namp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditableDTO {

    //private long idUser;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String address;
    private String phone;
}
