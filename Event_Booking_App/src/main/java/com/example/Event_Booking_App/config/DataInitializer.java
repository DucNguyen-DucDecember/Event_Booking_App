package com.example.Event_Booking_App.config;

import com.example.Event_Booking_App.entity.Role;
import com.example.Event_Booking_App.entity.User;
import com.example.Event_Booking_App.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@example.com";

            userRepository.findByEmail(email).ifPresentOrElse(user -> {
                // Nếu đã có admin, đảm bảo roles collection có ADMIN
                if (!user.getRoles().contains(Role.ADMIN)) {
                    user.getRoles().add(Role.ADMIN);
                    // Đồng thời, nếu chưa sync cột đơn role thì gán luôn:
                    user.setRole(Role.ADMIN);
                    userRepository.save(user);
                    System.out.println("Added ADMIN role to existing user: " + email);
                }
            }, () -> {
                // Nếu chưa có account, tạo mới luôn như cũ
                User admin = new User();
                admin.setFullName("System Administrator");
                admin.setEmail(email);
                admin.setPassword(passwordEncoder.encode("AdminPass123"));
                admin.setRole(Role.ADMIN);
                admin.getRoles().add(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Initialized default admin: " + email);
            });
        };
    }
}
