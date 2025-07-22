package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.dto.JwtResponse;
import com.example.Event_Booking_App.dto.LoginRequest;
import com.example.Event_Booking_App.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import com.example.Event_Booking_App.dto.*;
import com.example.Event_Booking_App.entity.Role;
import com.example.Event_Booking_App.entity.User;
import com.example.Event_Booking_App.exception.EmailAlreadyExistsException;
import com.example.Event_Booking_App.repository.UserRepository;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
@Service
public class AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtils jwtUtils;

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public JwtResponse authenticate(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        String token = jwtUtils.generateToken(auth.getName());
        Instant exp = jwtUtils.getExpirationFromToken(token);
        return new JwtResponse(token, exp.toString());
    }

    // --- new register ---
    public RegisterResponse register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        user.setRoles(Set.of(Role.USER));
        User saved = userRepository.save(user);

        return new RegisterResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail()
        );
    }
}
