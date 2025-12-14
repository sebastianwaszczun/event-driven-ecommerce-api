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

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest){ //RequestBody mapuje JSONA na obiekt Jav
        User entity = userMapper.toEntity(userRequest);
        User user = userService.addUser(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(user));
    }

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
        User entity = userMapper.toEntity(userRequest);
        User user = userService.editUser(id, entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> userInfo(@PathVariable Long id){
        if (userService.userInfo(id) != null){
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok(userMapper.toResponse(userService.userInfo(id)));
    }

}
