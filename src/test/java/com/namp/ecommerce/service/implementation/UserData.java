package com.namp.ecommerce.service.implementation;

import java.util.Arrays;
import java.util.List;

import com.namp.ecommerce.dto.UserAnswerDTO;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;
import com.namp.ecommerce.model.Role;
import com.namp.ecommerce.model.User;

/**
 * Datos de prueba que son utilizados por la clase UserTest
 */
public class UserData {
    
    public final static List<User> USERS = Arrays.asList(
        User.builder()
        .idUser(1L)
        .name("Agustin")
        .lastname("Anil")
        .email("agustin.anil@gmail.com")
        .address("La Rioja 444")
        .phone("3534233348")
        .username("AgustinAnil")
        .role(Role.ADMIN)
        .build(),
        User.builder()
        .idUser(2L)
        .name("Roberto")
        .lastname("Salera")
        .email("roberto.salera@gmail.com")
        .address("La Rioja 333")
        .phone("3534869254")
        .username("RobertoSalera")
        .role(Role.USER)
        .build()
    );

    public final static List<UserAnswerDTO> USERS_ANSWER_DTO = Arrays.asList(
        UserAnswerDTO.builder()
        .idUser(1L)
        .name("Agustin")
        .lastname("Anil")
        .email("agustin.anil@gmail.com")
        .address("La Rioja 444")
        .phone("3534233348")
        .username("AgustinAnil")
        .role(Role.ADMIN)
        .build(),
        UserAnswerDTO.builder()
        .idUser(2L)
        .name("Roberto")
        .lastname("Salera")
        .email("roberto.salera@gmail.com")
        .address("La Rioja 333")
        .phone("3534869254")
        .username("RobertoSalera")
        .role(Role.USER)
        .build()
    );

    public final static List<UserDTO> USERS_DTO = Arrays.asList(
        UserDTO.builder()
        .idUser(1L)
        .name("Agustin")
        .lastname("Anil")
        .email("agustin.anil@gmail.com")
        .address("La Rioja 444")
        .phone("3534233348")
        .username("AgustinAnil")
        .role(Role.ADMIN)
        .build(),
        UserDTO.builder()
        .idUser(2L)
        .name("Roberto")
        .lastname("Salera")
        .email("roberto.salera@gmail.com")
        .address("La Rioja 333")
        .phone("3534869254")
        .username("RobertoSalera")
        .role(Role.USER)
        .build()
    );


    public final static UserEditableDTO USER_EDITABLE_DTO = (
        new UserEditableDTO("Agustin", "Anil", "AGUSANIL","agustin123","agustin.anil@hotmail.com", "La Rioja 444", "3534233348"));

}
