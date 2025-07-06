package com.exceptionj.todos.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Data
public class UserAuthorities implements GrantedAuthority {

    private String authority;
}
