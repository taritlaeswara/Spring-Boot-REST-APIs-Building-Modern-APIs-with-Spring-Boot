package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.entity.UserAuthorities;
import com.exceptionj.todos.repository.UserRepository;
import com.exceptionj.todos.response.UserResponse;
import com.exceptionj.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public AdminServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()&& user.get().getAuthorities().stream().anyMatch(userAuthorities -> "ROLE_ADMIN".equals(userAuthorities.getAuthority()))){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found or already an admin");
        }
        List<UserAuthorities> authorities = new ArrayList<>();
        authorities.add(new UserAuthorities("ROLE_EMPLOYEE"));
        authorities.add(new UserAuthorities("ROLE_ADMIN"));
        user.get().setAuthorities(authorities);
        userRepository.save(user.get());
        return convertToResponse(user.get());
    }

    @Override
    @Transactional
    public void deleteNonAdminUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()&& user.get().getAuthorities().stream().anyMatch(userAuthorities -> "ROLE_ADMIN".equals(userAuthorities.getAuthority()))){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found or already an admin");
        }
        userRepository.delete(user.get());
    }

    private UserResponse convertToResponse(User user){

        return new UserResponse(user.getId(),user.getFirstName()+" "+user.getLastName(),user.getEmail(),user.getAuthorities());
    }
}
