package com.namp.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.namp.ecommerce.jwt.JwtAuthenticationFilter;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(csfr->
            csfr
            .disable())
            .authorizeHttpRequests(authRequest ->
                authRequest
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api-namp/product").hasAnyRole("USER","ADMIN")
                .requestMatchers("/api-namp/category").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Ninguna sesion se mantiene en el servidor, hay que autenticar cada solicitud de manera independiente
            .authenticationProvider(authProvider) // Se encarga de verificar las credenciales de los usuarios 
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class) // Filtro por defecto de spring security para validar usuario y contraseña
            .build();
    }
}
