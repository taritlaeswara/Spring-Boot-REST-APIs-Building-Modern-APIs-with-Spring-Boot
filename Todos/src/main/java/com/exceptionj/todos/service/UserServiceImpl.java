package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.entity.UserAuthorities;
import com.exceptionj.todos.repository.UserRepository;
import com.exceptionj.todos.request.PasswordUpdateRequest;
import com.exceptionj.todos.response.UserResponse;
import com.exceptionj.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final FindAuthenticatedUser findAuthenticatedUser;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {

        User user = findAuthenticatedUser.getAuthenticatedUser();
        return new UserResponse(user.getId(),
                user.getFirstName()+" "+user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth->(UserAuthorities)auth).toList());
    }

    @Override
    public void deleteUser() throws AccessDeniedException {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        //isLastAdmin
        if(isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete the last admin user");
        }

        userRepository.delete(user);

    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest request) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        if (!isOldPasswordCorrect(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }
        if (!isNewPasswordConfirmed(request.getNewPassword(), request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirm password do not match");
        }
        if (!isNewPasswordDifferent(request.getOldPassword(), request.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the old password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private boolean isOldPasswordCorrect(String currentPassword,String oldPassword){
        return passwordEncoder.matches(currentPassword, oldPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String confirmPassword){
        return newPassword.equals(confirmPassword);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword){
        return !oldPassword.equals(newPassword);
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
