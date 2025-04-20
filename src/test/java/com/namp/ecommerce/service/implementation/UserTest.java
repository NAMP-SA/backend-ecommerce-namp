package com.namp.ecommerce.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.namp.ecommerce.dto.UserAnswerDTO;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;
import com.namp.ecommerce.mapper.MapperUser;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;

/**
 * Test de clase User
 */
@ExtendWith(MockitoExtension.class)
public class UserTest {
    
    private final UserDTO userDTO = UserData.USERS_DTO.get(0);
    private final User user = UserData.USERS.get(0);

    @Mock
    IUserDAO repository;

    @Mock
    MapperUser mapperUser;

    @InjectMocks
    UserImplementation service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers() {

        when(repository.findAll()).thenReturn(UserData.USERS);

        for (int i = 0; i < (UserData.USERS.size()); i++){

            when(mapperUser.convertUserToUserAnswerDTO(UserData.USERS.get(i))).thenReturn(UserData.USERS_ANSWER_DTO.get(i));

        }

        List<UserAnswerDTO> users = service.getUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(users.get(0), UserData.USERS_ANSWER_DTO.get(0));
        assertEquals(users.get(1), UserData.USERS_ANSWER_DTO.get(1));

    }

    @Test
    void getUser() {

        when(repository.findByIdUser(1L)).thenReturn(UserData.USERS.get(0));

        when(mapperUser.convertUserToUserAnswerDTO(user)).thenReturn(UserData.USERS_ANSWER_DTO.get(0));

        UserAnswerDTO user = service.getUserById(1L);

        assertNotNull(user);
        assertEquals(user, UserData.USERS_ANSWER_DTO.get(0));

    }

    @Test
    void createUser() {

        //  No existe ningun usuario registrado con username o email
        when(repository.findAll()).thenReturn(List.of());
        
        when(mapperUser.convertUserDTOToUser(userDTO)).thenReturn(user);

        when(repository.save(user)).thenReturn(user);

        when(mapperUser.convertUserToUserDTO(user)).thenReturn(userDTO);

        UserDTO result = service.save(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getUsername(), result.getUsername());
    }

    @Test
    void updateUser() {

        when(repository.findByIdUser(1L)).thenReturn(user);

        when(repository.findAll()).thenReturn(List.of());

        when(service.update(1L, UserData.USER_EDITABLE_DTO)).thenReturn(UserData.USER_EDITABLE_DTO);

        UserEditableDTO result = service.update(1L, UserData.USER_EDITABLE_DTO);

        assertNotNull(result);
        assertEquals("AGUSANIL", result.getUsername());
    }

    @Test
    void deleteUser(){

        when(repository.findByIdUser(1L)).thenReturn(user);

        service.delete(1L);

        verify(repository).delete(user);

    }

}
