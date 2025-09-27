package com.exceptionj.todos.response;

import com.exceptionj.todos.entity.UserAuthorities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private List<UserAuthorities> authorities;
    public UserResponse(Long id, String fullName, String email, List<UserAuthorities> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorities = authorities;
    }
}
