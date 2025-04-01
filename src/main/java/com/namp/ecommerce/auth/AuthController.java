package com.namp.ecommerce.auth;

import com.namp.ecommerce.service.IBlackListService;
import com.namp.ecommerce.service.IJwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.namp.ecommerce.service.IAuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final IAuthService authService;

    @Autowired
    private IBlackListService blacklistService;

    @Autowired
    private IJwtService jwtService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
        if (response == null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This user already exists");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token){
        // Eliminar el prefijo "Bearer " del token
        String jwtToken = token.replace("Bearer ", "").trim();

        // Extraer el tiempo de expiración del token
        long expirationTime = jwtService.getExpirationTime(jwtToken);

        // Agregar el token a la lista negra
        blacklistService.addToBlacklist(jwtToken, expirationTime);

        return ResponseEntity.ok("Logout exitoso.");
    }

    // Manejador de excepciones específico para este controlador
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.addError(new ValidationError(fieldName, errorMessage));
        });

        return ResponseEntity.badRequest().body(errors);
    }
}

// Clases auxiliares
@Data
class ValidationErrorResponse {
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
        this.errors.add(error);
    }
}

@Data
@AllArgsConstructor
class ValidationError {
    private String field;
    private String message;
}