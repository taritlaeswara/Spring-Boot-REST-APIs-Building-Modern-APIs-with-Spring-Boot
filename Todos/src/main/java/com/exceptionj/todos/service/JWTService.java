package com.exceptionj.todos.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String,Object> claims,UserDetails userDetails);

}
