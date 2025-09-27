package com.exceptionj.todos.controller;

import com.exceptionj.todos.request.AuthenticationRequest;
import com.exceptionj.todos.request.RegisterRequest;
import com.exceptionj.todos.response.AuthenticationResponse;
import com.exceptionj.todos.service.AuthenicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication", description = "Endpoints for user authentication and authorization")
public class AuthenticationController {
    private final AuthenicationService authenicationService;

    public AuthenticationController(AuthenicationService authenicationService) {
        this.authenicationService = authenicationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid  @RequestBody RegisterRequest registerRequest) throws Exception {
        authenicationService.register(registerRequest);
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token upon successful login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest ) throws Exception {
        return authenicationService.login(authenticationRequest);
    }
}
