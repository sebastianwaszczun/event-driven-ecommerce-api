package com.waszczun.sebastian.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "First name is required")
        @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        // Telefon może być opcjonalny (bez @NotBlank), ale jeśli user go poda,
        // to możesz dodać walidację formatu (np. cyfry). Tu zostawiam luźno.
        String phoneNumber
) {
}
