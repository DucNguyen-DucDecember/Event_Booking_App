package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.entity.User;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final Set<GrantedAuthority> authorities;

    public UserDetailsImpl(User user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Sử dụng email thay vì username
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
    public boolean isEnabled() {
        return true;
    }
}