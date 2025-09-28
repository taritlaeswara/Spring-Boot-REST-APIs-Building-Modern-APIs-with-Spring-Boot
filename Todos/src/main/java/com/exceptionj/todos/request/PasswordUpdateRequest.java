package com.exceptionj.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {

    @NotEmpty(message = "Old Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String oldPassword;
    @NotEmpty(message = "New Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String newPassword;
    @NotEmpty(message = "Confirm Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String confirmPassword;

}
