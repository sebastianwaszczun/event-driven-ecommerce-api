package com.waszczun.sebastian.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waszczun.sebastian.ecommerce.config.SecurityConfig;
import com.waszczun.sebastian.ecommerce.dto.AuthResponse;
import com.waszczun.sebastian.ecommerce.dto.RegisterRequest;
import com.waszczun.sebastian.ecommerce.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AuthService authService;

    @Test
    void register_ShouldReturnRegisteredUser() throws Exception{
        //GIVEN
        RegisterRequest registerRequest = new RegisterRequest("Adam", "Nowak", "adam.nowak@gmail.com", "mojeTajneHaslo", "123456789");
        AuthResponse authResponse = new AuthResponse("to-moj-token");
        when(authService.register(registerRequest)).thenReturn(authResponse);

        //THEN
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("to-moj-token"));
    }

    @Test
    void register_ShouldReturnBadRequest_WhenEmailIsInvalid() throws Exception{
        //GIVEN
        RegisterRequest registerRequest = new RegisterRequest("Adam", "Nowak", "zly-format-emaila", "mojeTajneHaslo", "123456789");

        //THEN
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());


    }

}
