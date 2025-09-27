package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.entity.UserAuthorities;
import com.exceptionj.todos.repository.UserRepository;
import com.exceptionj.todos.request.AuthenticationRequest;
import com.exceptionj.todos.request.RegisterRequest;
import com.exceptionj.todos.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenicationServiceImpl implements AuthenicationService{

    private final UserRepository userRepository;

    private final PasswordEncoder  passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final  JWTService jwtService;

    public AuthenicationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @Override
    @Transactional
    public void register(RegisterRequest input) throws Exception {
        if(isEmailAlreadyInUse(input.getEmail())){
            throw new Exception("Email is already in use");
        }
        User user = buildNewUser(input);
        assignAuthorities(user);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new Exception("User not found"));
        String jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return new AuthenticationResponse(jwtToken);
    }

    private void assignAuthorities(User user) {
    }

    private User buildNewUser(RegisterRequest input) {
        User user = new User();
        user.setId(0L);
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setAuthorities(initializeAuthorities());
        return user;
    }

    private List<UserAuthorities> initializeAuthorities(){
        boolean isFirstUser = userRepository.count() == 0;
        List<UserAuthorities> authorities = new ArrayList<>();
        UserAuthorities userAuthority = new UserAuthorities();
        authorities.add(new UserAuthorities("ROLE_EMPLOYEE"));
        if(isFirstUser){
            authorities.add(new UserAuthorities("ROLE_ADMIN"));
        }

        return authorities;
    }

    private boolean isEmailAlreadyInUse(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
