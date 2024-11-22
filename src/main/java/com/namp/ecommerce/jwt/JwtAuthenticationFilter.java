package com.namp.ecommerce.jwt;

import java.io.IOException;

import com.namp.ecommerce.service.IBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.namp.ecommerce.service.IJwtService;
import com.namp.ecommerce.service.implementation.CustomUserDetailsService;

import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private final IJwtService jwtService;

    private final CustomUserDetailsService userDetailsService; 

    @Autowired
    private IBlackListService blackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "").trim();

            // Verificar si el token está en la lista negra
            if (blackListService.isTokenBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return;
            }

            // Verificar si el token es válido y obtener el nombre de usuario
            if (jwtService.validateToken(token)) {
                String username = jwtService.getUsernameFromToken(token); // Extraemos el username del token

                // Si el username no es nulo y no hay autenticación activa en el SecurityContext
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Cargar los detalles del usuario
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Verificar que el token es válido para este usuario
                    if (jwtService.isTokenValid(token, userDetails)) {
                        // Crear un objeto de autenticación
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                        // Establecer los detalles de la autenticación
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Establecer el contexto de autenticación
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return;
            }
        }

        filterChain.doFilter(request, response); // Continuar con el filtro
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null; 
    }

    
}
