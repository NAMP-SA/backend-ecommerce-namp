package com.namp.ecommerce.service;

public interface IBlackListService {
    void addToBlacklist(String token, long expirationTime);
    boolean isTokenBlacklisted(String token);
    void cleanExpiredTokens();
}
