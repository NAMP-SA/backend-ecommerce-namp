package com.namp.ecommerce.service;

import com.namp.ecommerce.Auth.AuthResponse;
import com.namp.ecommerce.Auth.LoginRequest;
import com.namp.ecommerce.Auth.RegisterRequest;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
