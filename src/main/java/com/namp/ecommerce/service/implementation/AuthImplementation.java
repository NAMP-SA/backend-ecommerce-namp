package com.namp.ecommerce.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.Auth.AuthResponse;
import com.namp.ecommerce.Auth.LoginRequest;
import com.namp.ecommerce.Auth.RegisterRequest;
import com.namp.ecommerce.model.Role;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IUserDAO;
import com.namp.ecommerce.service.IAuthService;
import com.namp.ecommerce.service.IJwtService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthImplementation implements IAuthService {
    
    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private final IJwtService jwtService; 

    private final PasswordEncoder passwordEncoder; 
    private final AuthenticationManager authenticationManager; 

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userDAO.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userDAO.save(user); 

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }



}
