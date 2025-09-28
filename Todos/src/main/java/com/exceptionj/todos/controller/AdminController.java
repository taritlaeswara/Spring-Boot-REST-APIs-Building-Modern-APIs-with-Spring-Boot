package com.exceptionj.todos.controller;

import com.exceptionj.todos.response.UserResponse;
import com.exceptionj.todos.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Endpoints for admin operations")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users (admin only)")
    @GetMapping("/all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Operation(summary = "Promote user to admin", description = "Promotes a user to admin role by user ID (admin only)")
    @GetMapping("/{userId}/role")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse promoteToAdmin(@PathVariable @Min(1) Long userId) {
        return adminService.promoteToAdmin(userId);
    }

    @Operation(summary = "Delete non-admin user", description = "Deletes a non-admin user by user ID (admin only)")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNonAdminUsers(@PathVariable @Min(1) Long userId) {
        adminService.deleteNonAdminUser(userId);
    }
}
