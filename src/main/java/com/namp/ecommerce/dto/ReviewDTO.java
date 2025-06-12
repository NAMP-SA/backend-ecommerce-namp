package com.namp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private long idReview;
    private String subject;
    private String message;
    private UserDTO idUser;

}
