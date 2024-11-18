package com.namp.ecommerce.service;


import com.namp.ecommerce.dto.UserAnswerDTO;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;

import java.util.List;

public interface IUserService {

    List<UserAnswerDTO> getUsers();
    UserDTO save(UserDTO userDTO);
    void delete(long id);
    UserEditableDTO update(long id, UserEditableDTO userEditableDTO);
    boolean verifyUsername(String normalizedUsername);
    boolean verifyUsername(String normalizedUsername, long id);
    boolean verifyEmail(String normalizedEmail);
    boolean verifyEmail(String normalizedEmail, long id);
    boolean findById(long id);
    UserAnswerDTO getUserById(long id);
    UserDTO findByUsername(String username);
}
