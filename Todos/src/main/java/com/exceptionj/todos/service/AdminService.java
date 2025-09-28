package com.exceptionj.todos.service;

import com.exceptionj.todos.response.UserResponse;

import java.util.List;

public interface AdminService {
    List<UserResponse> getAllUsers();
    UserResponse promoteToAdmin(Long id);
    void deleteNonAdminUser(Long id);

}
