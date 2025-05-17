package com.example.product.configs;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        blacklist.add(token);
        System.out.println("Token blacklist: " + token + " | Hash: " + token.hashCode());
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
