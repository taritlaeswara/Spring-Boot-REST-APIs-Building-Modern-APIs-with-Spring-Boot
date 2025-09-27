package com.exceptionj.todos.service;

import com.exceptionj.todos.request.AuthenticationRequest;
import com.exceptionj.todos.request.RegisterRequest;
import com.exceptionj.todos.response.AuthenticationResponse;

public interface AuthenicationService {
    void register(RegisterRequest input) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request) throws Exception;
}
