package com.waszczun.sebastian.ecommerce.dto;

public record UserResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
){

}
