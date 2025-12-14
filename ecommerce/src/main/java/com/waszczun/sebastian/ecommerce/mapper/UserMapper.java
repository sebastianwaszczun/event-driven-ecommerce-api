package com.waszczun.sebastian.ecommerce.mapper;

import com.waszczun.sebastian.ecommerce.dto.UserRequest;
import com.waszczun.sebastian.ecommerce.dto.UserResponse;
import com.waszczun.sebastian.ecommerce.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setPhoneNumber(userRequest.phoneNumber());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber());
    }
}
