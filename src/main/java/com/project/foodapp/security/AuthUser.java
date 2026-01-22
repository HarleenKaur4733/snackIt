package com.project.foodapp.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.foodapp.auth_users.entity.User;

import lombok.Builder;
import lombok.Data;

// Maps our User entity to Spring Security's UserDetails
// Because it expects object in certain format

// ------------Here is the flow: ------------
// 1. Request comes (login / JWT request)
// 2. Spring Security needs a user
// 3. Spring calls loadUserByUsername(username)
// 4. You fetch User from DB (using repository)
// 5. You convert User → AuthUser (UserDetails)
// 6. You return AuthUser to Spring Security
// 7. Spring does authentication & authorization

@Builder
@Data
public class AuthUser implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public boolean isEnabled() {
        return user.isActive();
    }

}
