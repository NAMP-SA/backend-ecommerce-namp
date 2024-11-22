package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.service.IBlackListService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlackListImplementation implements IBlackListService {

    // Mapa seguro para subprocesos que almacena tokens junto con su tiempo de expiración
    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();

    @Override
    public void addToBlacklist(String token, long expirationTime) {
        blacklistedTokens.put(token, expirationTime);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.containsKey(token);
    }

    @Scheduled(fixedRate = 60000) // Cada 60 segundos
    public void cleanExpiredTokens() {
        long currentTime = Instant.now().toEpochMilli();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue() < currentTime);
    }
}
