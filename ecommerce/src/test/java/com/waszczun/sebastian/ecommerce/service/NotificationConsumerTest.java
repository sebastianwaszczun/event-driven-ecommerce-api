package com.waszczun.sebastian.ecommerce.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationConsumerTest {
    private final NotificationConsumer notificationConsumer = new NotificationConsumer();

    @Test
    void shouldConsumeMessageWithoutThrowingException(){
        //GIVEN
        String testEmail = "adam.nowak@gmail.com";

        //THEN
        assertDoesNotThrow(()->notificationConsumer.consume(testEmail));

    }
}
