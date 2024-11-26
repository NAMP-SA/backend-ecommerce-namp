package com.namp.ecommerce.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserImplementationTest {

    private static Validator validator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserDAO userDAO;

    // Este service no ejecuta la logica real
    @MockBean
    private UserImplementation userService;

    @InjectMocks
    private UserImplementation userServiceLogic;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(userDAO);
    }

    @Test public void testUsernameAlphanumeric() {
        // Creo objeto
        User user = new User();
        user.setUsername("Juan123");

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");

        assertTrue(violations.isEmpty(), "No debería haber una violación de validación");
    }

    @Test public void testUsernameNotAlphanumeric() {
        // Creo objeto
        User user = new User();
        user.setUsername("123");

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testUsernameLenghtLimit() {
        // Creo objeto
        User user = new User();
        user.setUsername("ElExploradorGalacticoSupremo");

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testUsernameSpecialCharacters() {
        // Creo objeto
        User user = new User();
        user.setUsername("Agu#stin");

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }

    @Test public void testUsernameEmpty() {
        // Creo objeto
        User user = new User();
        user.setUsername("  ");

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");

        assertEquals(1, violations.size(), "Mensaje de violacion de validacion");
    }


    @Test public void testVerifyUsername_Exist() {
        // Simulo que existen objetos en la base de datos ya almacenados
        User user1 = new User(1L, "Agustin", "Anil", "agustin@gmail,com", "La Rioja", "232454444", "USUARIO1", "123");
        List<User> mockUsers = Arrays.asList(user1);

        // Configuro el comportamiento del mock para que findAll() devuelva la lista simulada
        when(userDAO.findAll()).thenReturn(mockUsers);
        System.out.println();

        // Verifico que la funcionalidad de verifyUsername funcione correctamente
        boolean result = userServiceLogic.verifyUsername("USUARIO1");

        // Verifico que el resultado sea 'true' ya que 'USUARIO1' existe
        assertTrue(result, "This username alredy exists");
    }

    @Test public void testVerifyUsername_NotExist() {
        // Simulo que existen objetos en la base de datos ya almacenados
        User user1 = new User(1L, "Agustin", "Anil", "agustin@gmail,com", "La Rioja", "232454444", "USUARIO1", "123");
        List<User> mockUsers = Arrays.asList(user1);

        // Configuro el comportamiento del mock para que findAll() devuelva la lista simulada
        when(userDAO.findAll()).thenReturn(mockUsers);
        System.out.println();

        // Verifico que la funcionalidad de verifyUsername funcione correctamente
        boolean result = userServiceLogic.verifyUsername("USUARIO2");

        // Verifico que el resultado sea 'true' ya que 'USUARIO1' existe
        assertFalse(result, "Save successful");
    }

    @Test public void testEmailNotLimit_NotEmpty() throws Exception {

        UserDTO userDTO = new UserDTO(10, "Agustin", "Doe", "example@gmail.com", "123 Main",
                "1234567890", "johndoe", "12345678aa", "12345678aa");

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        String validUserJson = """
        {
            "idUser": 10,
            "name": "Agustin",
            "lastname": "Doe",
            "email": "example@gmail.com",
            "address": "123 Main",
            "phone": "1234567890",
            "username": "johndoe",
            "password": "12345678aa",
            "confirmPassword": "12345678aa"
        }
        """;

        // Realizar la solicitud POST con el JSON
        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson))
                .andExpect(status().isOk());
    }

    @Test public void testEmailInvalid() throws Exception {
        UserDTO userDTO = new UserDTO(11, "Gonzalo", "Doe", "example  @gmail.com", "123 Main", "1234567890", "johndoe", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }


    @Test public void testEmailLimit() throws Exception {
        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "ejemplo.correo.de.51.caracteres.en.total@gmail.com", "123 Main", "1234567890", "johndoe", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test public void testEmailEmpty() throws Exception {
        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "  ", "123 Main", "1234567890", "johndoe", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test public void testEmailAt() throws Exception {
        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "@", "123 Main", "1234567890", "johndoe", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test public void testEmailSpecial() throws Exception {
        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "prueba@!!.ss.com", "123 Main", "1234567890", "johndoe", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test public void testEmailAccept() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Agustin", "Doe", "prueba@ypf.com", "123 Main", "1234567890", "agustin", "12345678aa", "12345678aa");
        String jsonContent = new ObjectMapper().writeValueAsString(userDTO);

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test public void testPasswordMaxLimit_Upper() throws Exception {

        String validUserJson = """
        {
            "idUser": 12,
            "name": "Gonzalo",
            "lastname": "Doe",
            "email": "test@gmail.com",
            "address": "123 Main",
            "phone": "1234567890",
            "username": "johndoe",
            "password": "ContraseñaDeEjemplo123456",
            "confirmPassword": "ContraseñaDeEjemplo123456"
        }
        """;

        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson))
                .andExpect(status().isConflict())
                .andExpect(content().string("This user already exists"));
    }

    @Test public void testPasswordMaxLimit_Equal() throws Exception {

        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "test@gmail.com", "123 Main",
                "1234567890", "johndoe", "ContraseñaDeEjemplo12345", "ContraseñaDeEjemplo12345");

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        String validUserJson = """
        {
            "idUser": 12,
            "name": "Gonzalo",
            "lastname": "Doe",
            "email": "test@gmail.com",
            "address": "123 Main",
            "phone": "1234567890",
            "username": "johndoe",
            "password": "ContraseñaDeEjemplo1234",
            "confirmPassword": "ContraseñaDeEjemplo12345"
        }
        """;

    mockMvc.perform(post("/api-namp/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validUserJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.username").value("johndoe"));
    }

    @Test public void testPasswordMaxLimit_Under() throws Exception {

        UserDTO userDTO = new UserDTO(12, "Gonzalo", "Doe", "test@gmail.com", "123 Main",
                "1234567890", "johndoe", "ContraseñaDeEjemplo1234", "ContraseñaDeEjemplo1234");

        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        String validUserJson = """
        {
            "idUser": 12,
            "name": "Gonzalo",
            "lastname": "Doe",
            "email": "test@gmail.com",
            "address": "123 Main",
            "phone": "1234567890",
            "username": "johndoe",
            "password": "ContraseñaDeEjemplo1234",
            "confirmPassword": "ContraseñaDeEjemplo1234"
        }
        """;

        // Realizar la solicitud POST con el JSON
        mockMvc.perform(post("/api-namp/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.username").value("johndoe"));
    }

}

/*
 LISTADO DE NUEVOS CAMBIOS
    1. regexp = "^(?!\\d+$)[a-zA-Z0-9 ]*$" en username, ya que aceptaba solo numeros y ahora no.
    2. @NotBlank -> User.java (username), para que no acepte ninguna cadena con espacios.
    3. @Size(min = 8, max = 24, message = "La contrasena tiene que tener entre 8 y 24 caracteres")
 */
