package com.namp.ecommerce.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.namp.ecommerce.dto.UserDTO;

import java.util.function.Function;
import java.util.Date;
import io.jsonwebtoken.Claims;
import java.security.Key;

public interface IJwtService {
    String getToken(UserDTO user); 
    String getToken(Map<String,Object> extraClaims, UserDTO user);
    Key getKey(); 
    String getUsernameFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails); 
    Claims getAllClaims(String token);
    <T> T getClaim(String token, Function<Claims,T> claimsResolver); 
    Date getExpiration(String token);
    boolean isTokenExpired(String token); 
}
