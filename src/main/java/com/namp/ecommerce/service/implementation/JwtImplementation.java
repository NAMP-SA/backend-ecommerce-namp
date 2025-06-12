package com.namp.ecommerce.service.implementation;

import java.util.Map;

import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.service.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;


import io.github.cdimascio.dotenv.Dotenv;

@Service
@RequiredArgsConstructor
public class JwtImplementation implements IJwtService{

    private static final String SECRET_KEY;

    static {
        SECRET_KEY = System.getenv("JWT_SECRET");
        if (SECRET_KEY == null) {
            throw new IllegalStateException("JWT_SECRET environment variable is not set");
        }
    }

    @Override
    public String getToken(UserDTO user) {
        return getToken(new HashMap<>(),user);
    }


    public String getToken(Map<String,Object> extraClaims, UserDTO user) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(),SignatureAlgorithm.HS256)
            .compact(); 
    }

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); 
    }
    

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims); 
    }

    public Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    @Override
    public long getExpirationTime(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().getTime(); // Devuelve el tiempo de expiración en milisegundos
    }

    // Método para validar el token
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Si no lanza excepciones, el token es válido
        } catch (Exception e) {
            return false; // Si hay alguna excepción, el token no es válido
        }
    }

}
