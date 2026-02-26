package com.waszczun.sebastian.ecommerce.service;

import com.waszczun.sebastian.ecommerce.config.JwtTokenProvider;
import com.waszczun.sebastian.ecommerce.dto.AuthResponse;
import com.waszczun.sebastian.ecommerce.dto.RegisterRequest;
import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KafkaProducerService kafkaProducerService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email is already taken");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());

        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        kafkaProducerService.sendRegistrationEvent(user.getEmail());

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

}
