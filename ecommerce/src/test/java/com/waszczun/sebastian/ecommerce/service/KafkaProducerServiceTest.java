package com.waszczun.sebastian.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTest {
    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    KafkaProducerService kafkaProducerService;

    @Test
    void shouldReturnEmailRegistrationEvent(){
        //GIVEN
        String email = "adam.nowak@gmail.com";
        String topicName = "user-registrations";

        //WHEN
        kafkaProducerService.sendRegistrationEvent(email);

        //THEN
        verify(kafkaTemplate).send(topicName, email);
    }
}
