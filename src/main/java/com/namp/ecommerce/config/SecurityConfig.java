package com.namp.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.namp.ecommerce.jwt.JwtAuthenticationFilter;



import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authRequest ->
                authRequest
                //Public endpoint
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api-namp/**").permitAll()
                //USER role endpoints 
                .requestMatchers("/api-namp/user/**").hasAnyRole("USER","ADMIN")
                //ADMIN role endpoints
                .requestMatchers("/api-namp/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Ninguna sesion se mantiene en el servidor, hay que autenticar cada solicitud de manera independiente
            .authenticationProvider(authProvider) // Se encarga de verificar las credenciales de los usuarios 
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class) // Filtro por defecto de spring security para validar usuario y contraseña
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Cambia por el origen de tu frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization")); // Permitir encabezados expuestos
        configuration.setAllowCredentials(true); // Permitir cookies/credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar a todas las rutas
        return source;
    }
}
