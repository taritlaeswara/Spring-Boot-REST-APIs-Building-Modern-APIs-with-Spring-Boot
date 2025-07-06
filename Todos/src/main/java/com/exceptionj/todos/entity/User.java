package com.exceptionj.todos.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",unique = true,nullable = false,length = 100)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "created_at",updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updateOn;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities",
    joinColumns = @JoinColumn(name = "user_id"))
    private List<UserAuthorities> authorities;

    @OneToMany(mappedBy = "owner",
    cascade = CascadeType.ALL,orphanRemoval = true)
   private List<Todo> todos;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
