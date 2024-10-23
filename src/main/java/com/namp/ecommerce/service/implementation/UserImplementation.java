package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.CategoryDTO;
import com.namp.ecommerce.dto.UserAnswerDTO;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;
import com.namp.ecommerce.mapper.MapperUser;
import com.namp.ecommerce.model.Category;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;
import com.namp.ecommerce.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImplementation implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private MapperUser mapperUser;

    @Override
    public List<UserAnswerDTO> getUsers() {
        return userDAO.findAll()
                .stream()
                .map(mapperUser::convertUserToUserAnswerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {

        // Normalizo el nombre de usuario
        String normalizedUsername = userDTO.getUsername().replaceAll("\\s+", " ").trim().toUpperCase();

        if(!verifyName(normalizedUsername) || normalizedUsername.matches("\\d+")) {

            userDTO.setUsername(normalizedUsername);

            User user = mapperUser.convertUserDTOToUser(userDTO);

            User savedUser = userDAO.save(user);

            return mapperUser.convertUserToUserDTO(savedUser);
        }
        return null;
    }

    @Override
    public void delete(long id) {

        User user = userDAO.findByIdUser(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userDAO.delete(user);
    }

    @Override
    public UserEditableDTO update(long id, UserEditableDTO userEditableDTO) {

        if (!findById(id)) {
            return null;
        }
        User existingUser = userDAO.findByIdUser(id);

        if (existingUser != null) {
            existingUser.setName(userEditableDTO.getName());
            existingUser.setEmail(userEditableDTO.getEmail());
            existingUser.setAddress(userEditableDTO.getAddress());
            existingUser.setPhone(userEditableDTO.getPhone());

            userDAO.save(existingUser);

            return mapperUser.convertUserToUserEditableDTO(existingUser);
        }
        return null;

    }

    @Override
    public boolean verifyName(String normalizedUsername){
        List<User> users = userDAO.findAll();
        String userName = normalizedUsername.replaceAll("\\s+", "");

        //Comparar el username que se quiere guardar, con todos los demas sin espacio para ver si es el mismo
        for(User user : users){
            if(userName.equals(user.getUsername().replaceAll("\\s+", ""))){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean findById(long id) {
        User user = userDAO.findByIdUser(id);
        if (user == null) {
            return false;
        }
        //return mapperUser.convertUserToUserEditableDTO(user);
        return true;

    }

}
