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
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
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
    public UserAnswerDTO getUserById(long id) {
        User user = userDAO.findByIdUser(id);

        if (user == null){
            return null;
        }

        return mapperUser.convertUserToUserAnswerDTO(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        //Normalizo el email del usuario
        String normalizedEmail = userDTO.getEmail().replaceAll("\\s+", " ").trim().toUpperCase();
        // Normalizo el nombre de usuario
        String normalizedUsername = userDTO.getUsername().replaceAll("\\s+", " ").trim();

        if(!verifyUsername(normalizedUsername) || normalizedUsername.matches("\\d+")) {
            if(!verifyEmail(normalizedEmail)) {
                userDTO.setUsername(normalizedUsername);
                userDTO.setEmail(normalizedEmail);
                User user = mapperUser.convertUserDTOToUser(userDTO);
                User savedUser = userDAO.save(user);

                return mapperUser.convertUserToUserDTO(savedUser);
            }
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
        ///Chequear si usar id o objeto_real

        String normalizedEmail = userEditableDTO.getEmail().replaceAll("\\s+", " ").trim().toUpperCase();
        String normalizedUsername = userEditableDTO.getUsername().replaceAll("\\s+", " ").trim().toUpperCase();

        if (!findById(id)) {
            return null;
        }
        User existingUser = userDAO.findByIdUser(id);

        if (existingUser != null) {
            if(!verifyEmail(normalizedEmail,id) && !verifyUsername(normalizedUsername,id)) {
                
                existingUser.setName(userEditableDTO.getName());
                existingUser.setLastname(userEditableDTO.getLastname());
                existingUser.setUsername(normalizedUsername);
                String pass = DigestUtils.sha256Hex(userEditableDTO.getPassword());
                existingUser.setPassword(pass);

                existingUser.setEmail(normalizedEmail);
                existingUser.setAddress(userEditableDTO.getAddress());
                existingUser.setPhone(userEditableDTO.getPhone());

                userDAO.save(existingUser);
                return mapperUser.convertUserToUserEditableDTO(existingUser);
                
            }
        }
        return null;

    }

    @Override
    public boolean verifyUsername(String normalizedUsername){
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
    public boolean verifyUsername(String normalizedName, long userId) {
        List<User> users = userDAO.findAll();
        String name = normalizedName.replaceAll("\\s+", "");

        //Verifica si se repite el nombre en los demas usuarios, menos con la que se está actualizando
        for (User user : users) {
            if (user.getIdUser() != userId && name.equals(user.getUsername().replaceAll("\s+", ""))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verifyEmail(String normalizedEmail){
        List<User> users = userDAO.findAll();
        String userEmail = normalizedEmail.replaceAll("\\s+", "");

        //Comparar el email que se quiere guardar, con todos los demas sin espacio para ver si es el mismo
        for(User user : users){
            if(userEmail.equals(user.getEmail().replaceAll("\\s+", ""))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyEmail(String normalizedEmail, long userId){
        List<User> users = userDAO.findAll();
        String userEmail = normalizedEmail.replaceAll("\\s+", "");

        //Verifica si se repite el email en los demas usuarios, menos con el que se está actualizando
        for(User user : users){
            if(user.getIdUser() != userId && userEmail.equals(user.getEmail().replaceAll("\\s+", ""))){
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

    @Override
    public UserDTO findByUsername(String username){
        User user = userDAO.findByUsername(username).orElseThrow();

        return mapperUser.convertUserToUserDTO(user);
    }
}
