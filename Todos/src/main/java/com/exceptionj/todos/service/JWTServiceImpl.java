package com.exceptionj.todos.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public class JWTServiceImpl implements JWTService{
    @Override
    public String extractUserName(String token) {
        return "";
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return "";
    }
}
