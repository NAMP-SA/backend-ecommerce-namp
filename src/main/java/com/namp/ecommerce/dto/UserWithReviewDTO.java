package com.namp.ecommerce.dto;

import com.namp.ecommerce.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithReviewDTO {


    private long idUser;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private Role role;

    private List<ReviewDTO> reviews;
}
