package com.namp.ecommerce.service;

import com.namp.ecommerce.auth.AuthResponse;
import com.namp.ecommerce.auth.LoginRequest;
import com.namp.ecommerce.auth.RegisterRequest;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
