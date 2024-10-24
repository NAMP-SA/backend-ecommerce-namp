package com.namp.ecommerce.mapper;

import com.namp.ecommerce.dto.UserAnswerDTO;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;
import com.namp.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {

    @Autowired
    private MapperUtil mapperUtil;

    // Metodo para convertir un DTO a UserAnswer


    public User convertUserDTOToUser(UserDTO userDTO){
        User user = new User();

        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return  user;
    }

        /*
    ----------------------------------------------------------------------------------------------------------
                                             TO DTO
   -----------------------------------------------------------------------------------------------------------
    */
    public UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = mapperUtil.convertUserToUserDTO(user);

        return userDTO;
    }

    public UserAnswerDTO convertUserToUserAnswerDTO(User user){
        UserAnswerDTO userAnswerDTO = mapperUtil.convertUserToUserAnswerDTO(user);
        return  userAnswerDTO;
    }

    public UserEditableDTO convertUserToUserEditableDTO(User user){
        UserEditableDTO userEditableDTO = mapperUtil.convertUserToUserEditableDTO(user);

        return userEditableDTO;
    }
}
