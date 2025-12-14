package com.waszczun.sebastian.ecommerce.controller;

import com.waszczun.sebastian.ecommerce.dto.UserRequest;
import com.waszczun.sebastian.ecommerce.dto.UserResponse;
import com.waszczun.sebastian.ecommerce.mapper.UserMapper;
import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){
        List<UserResponse> responses = userService.getUsers()
                .stream()
                .map(userMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> editUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        User user = userService.userInfo(id);
        userMapper.toEntity(userRequest, user);
        User user1 = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toResponse(user1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> userInfo(@PathVariable Long id){
        if (userService.userInfo(id) == null){
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok(userMapper.toResponse(userService.userInfo(id)));
    }

}
