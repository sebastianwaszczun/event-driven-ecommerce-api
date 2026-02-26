package com.waszczun.sebastian.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "user-registrations", groupId = "ecommerce-group")
    public void consume(String email) {
        LOGGER.info("Odebrano zdarzenie z Kafki! Symulacja wysyłki e-maila powitalnego do: {}", email);
    }
}
