package com.waszczun.sebastian.ecommerce.dto;

import java.math.BigDecimal;

public record ProductResponse (
        long id,
        String name,
        String description,
        String category,
        BigDecimal stockQuantity,
        int price
){}
