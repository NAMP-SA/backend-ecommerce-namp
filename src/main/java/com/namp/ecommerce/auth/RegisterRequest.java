package com.namp.ecommerce.auth;

import com.namp.ecommerce.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String name;
    String lastname;
    String email;
    String address; 
    String phone;
    String username;
    String password;
    Role role; 
}
