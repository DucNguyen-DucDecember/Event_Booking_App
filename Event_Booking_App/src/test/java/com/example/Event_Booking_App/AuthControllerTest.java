package com.example.Event_Booking_App;

import com.example.Event_Booking_App.controller.AuthController;
import com.example.Event_Booking_App.dto.*;
import com.example.Event_Booking_App.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoMockBean
    private AuthService authService;

    @Test
    void testLoginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testemail@example.com");
        loginRequest.setPassword("password");

        JwtResponse jwtResponse = new JwtResponse("mocked-jwt-token", "2030-12-31T23:59:59Z");

        Mockito.when(authService.authenticate(Mockito.any(LoginRequest.class)))
                .thenReturn(jwtResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.accessToken").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.data.expire").value("2030-12-31T23:59:59Z"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFullName("newuser");
        registerRequest.setEmail("newuser@example.com");
        registerRequest.setPassword("securepass");

        RegisterResponse registerResponse = new RegisterResponse(1L, "newuser", "newuser@example.com");

        Mockito.when(authService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(registerResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.fullName").value("newuser"))
                .andExpect(jsonPath("$.data.email").value("newuser@example.com"));
    }

    @Test
    void testLoginValidationFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest();  // thiếu email + password

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterValidationFail() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();  // thiếu trường required

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }
}
