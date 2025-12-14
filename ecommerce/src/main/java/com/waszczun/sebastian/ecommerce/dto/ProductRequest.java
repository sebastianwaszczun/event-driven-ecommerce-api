package com.waszczun.sebastian.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank (message = "Category is required")
        String category,

        @DecimalMin(value = "0.01", message = "Price musy be greater than 0.1")
        BigDecimal price,

        @NotNull(message = "Stock quantity is required")
        int stockQuantity
){}
