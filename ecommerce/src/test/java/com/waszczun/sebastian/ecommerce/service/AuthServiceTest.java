package com.waszczun.sebastian.ecommerce.service;

import com.waszczun.sebastian.ecommerce.config.JwtTokenProvider;
import com.waszczun.sebastian.ecommerce.dto.AuthResponse;
import com.waszczun.sebastian.ecommerce.dto.RegisterRequest;
import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.RuntimeErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    KafkaProducerService kafkaProducerService;

    @InjectMocks
    AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully(){
        //GIVEN
        RegisterRequest registerRequest = new RegisterRequest("Adam", "Nowak", "adam.nowaczi@gmail.com", "mojeTajneHaslo", "123456789");
        when(userRepository.existsByEmail("adam.nowaczi@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("mojeTajneHaslo")).thenReturn("ZASZYFROWANE_HASLO");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(jwtTokenProvider.generateToken("adam.nowaczi@gmail.com")).thenReturn("super-tajny-token");

        //WHEN
        AuthResponse register = authService.register(registerRequest);

        //THEN
        assertEquals("super-tajny-token", register.token());
        verify(userRepository).save(any(User.class));
        verify(kafkaProducerService).sendRegistrationEvent("adam.nowaczi@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyTaken(){
        RegisterRequest registerRequest = new RegisterRequest("Adam", "Nowak", "adam.nowaczi@gmail.com", "mojeTajneHaslo", "123456789");
        when(userRepository.existsByEmail("adam.nowaczi@gmail.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
        verify(kafkaProducerService, never()).sendRegistrationEvent("adam.nowaczki@gmail.com");
    }
}
