package com.namp.ecommerce.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.auth.AuthResponse;
import com.namp.ecommerce.auth.LoginRequest;
import com.namp.ecommerce.auth.RegisterRequest;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.mapper.MapperUser;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;
import com.namp.ecommerce.service.IAuthService;
import com.namp.ecommerce.service.IJwtService;
import com.namp.ecommerce.service.IUserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthImplementation implements IAuthService {
    
    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IUserService userService; 

    @Autowired
    private final IJwtService jwtService; 

    @Autowired
    private MapperUser mapperUser;


    private final AuthenticationManager authenticationManager; 

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDTO userDTO = userService.findByUsername(request.getUsername());

        String token = jwtService.getToken(userDTO);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        UserDTO userDTO = UserDTO.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .confirmPassword(request.getConfirmPassword())
            .name(request.getName())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .phone(request.getPhone())
            .address(request.getAddress())
            .role(request.getRole())
            .build();

        
        userService.save(userDTO);

       

        return AuthResponse.builder()
            .token(jwtService.getToken(userDTO))
            .build();
    }



}
