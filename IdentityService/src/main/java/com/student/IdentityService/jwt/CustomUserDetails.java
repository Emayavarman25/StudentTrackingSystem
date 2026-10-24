package com.student.IdentityService.jwt;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.student.IdentityService.model.Users;

public class CustomUserDetails implements UserDetails {

    private final Users user;
    public String getEmail() 
    {
    return user.getEmail();
}

public String getMobile() {
    return user.getMobile();
}

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public Users getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
            .flatMap(role -> Stream.concat(
                Stream.of(
                    new SimpleGrantedAuthority(role.getName())
                ),
                role.getPrivileges().stream()
                    .map(privilege ->
                        new SimpleGrantedAuthority(privilege.getName())
                    )
            ))
            .collect(Collectors.toSet());
    }
}