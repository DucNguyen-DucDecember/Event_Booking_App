package com.example.eba.service;

import com.example.eba.dto.JwtResponse;
import com.example.eba.dto.LoginRequest;
import com.example.eba.dto.RegisterRequest;
import com.example.eba.dto.RegisterResponse;
import com.example.eba.entity.Role;
import com.example.eba.entity.User;
import com.example.eba.exception.EmailAlreadyExistsException;
import com.example.eba.repository.UserRepository;
import com.example.eba.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

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

    public RegisterResponse register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // --- đồng bộ cả 2 chỗ ---
        user.setRole(Role.USER);
        user.getRoles().add(Role.USER);

        User saved = userRepository.save(user);
        return new RegisterResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail()
        );
    }

    /** Tạo admin mới (chỉ ADMIN mới gọi được) */
    public RegisterResponse registerAdmin(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        User admin = new User();
        admin.setFullName(req.getFullName());
        admin.setEmail(req.getEmail());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));

        // --- đồng bộ cả 2 chỗ ---
        admin.setRole(Role.ADMIN);
        admin.getRoles().add(Role.ADMIN);

        User saved = userRepository.save(admin);
        return new RegisterResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail()
        );
    }
}
