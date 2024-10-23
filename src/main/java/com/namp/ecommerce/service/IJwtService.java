package com.namp.ecommerce.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;
import java.util.Date;
import io.jsonwebtoken.Claims;
import java.security.Key;

public interface IJwtService {
    String getToken(UserDetails user); 
    String getToken(Map<String,Object> extraClaims, UserDetails user);
    Key getKey(); 
    String getUsernameFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails); 
    Claims getAllClaims(String token);
    <T> T getClaim(String token, Function<Claims,T> claimsResolver); 
    Date getExpiration(String token);
    boolean isTokenExpired(String token); 
}
