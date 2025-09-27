package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.entity.UserAuthorities;
import com.exceptionj.todos.repository.UserRepository;
import com.exceptionj.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required"); // or throw an exception based on your application's needs
        }
        User user = (User) authentication.getPrincipal();
        return new UserResponse(user.getId(),
                user.getFirstName()+" "+user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth->(UserAuthorities)auth).toList());
    }

    @Override
    public void deleteUser() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required"); // or throw an exception based on your application's needs
        }
        User user = (User) authentication.getPrincipal();

        //isLastAdmin
        if(isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete the last admin user");
        }

        userRepository.delete(user);

    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            return adminCount <= 1;
        }
        return false;
    }
}
