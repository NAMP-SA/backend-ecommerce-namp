package com.namp.ecommerce.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.namp.ecommerce.auth.AuthResponse;
import com.namp.ecommerce.auth.LoginRequest;
import com.namp.ecommerce.auth.RegisterRequest;
import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.service.IAuthService;
import com.namp.ecommerce.service.IJwtService;
import com.namp.ecommerce.service.IUserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthImplementation implements IAuthService {
    

    @Autowired
    private IUserService userService; 

    @Autowired
    private final IJwtService jwtService; 


    private final PasswordEncoder passwordEncoder;


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
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .phone(request.getPhone())
            .address(request.getAddress())
            .role(request.getRole())
            .build();


        UserDTO createdUser = userService.save(userDTO);
        if (createdUser !=null){
            return AuthResponse.builder().token(jwtService.getToken(userDTO)).build();
        }else{
            return null;
        }
       

    }



}
