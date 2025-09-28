package com.exceptionj.todos.service;

import com.exceptionj.todos.request.PasswordUpdateRequest;
import com.exceptionj.todos.response.UserResponse;
import org.springframework.security.access.AccessDeniedException;
public interface UserService {
    UserResponse getUserInfo() throws AccessDeniedException;

    void deleteUser() throws AccessDeniedException;

    void updatePassword(PasswordUpdateRequest request);

}
