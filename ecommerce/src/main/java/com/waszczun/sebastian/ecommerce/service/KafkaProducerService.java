package com.waszczun.sebastian.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendRegistrationEvent(String email) {
        LOGGER.info("Wysyłanie zdarzenia do Kafki: Nowy użytkownik -> {}", email);
        kafkaTemplate.send("user-registrations", email);
    }
}
