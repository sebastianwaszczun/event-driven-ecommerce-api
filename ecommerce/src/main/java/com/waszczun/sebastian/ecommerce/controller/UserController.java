package com.waszczun.sebastian.ecommerce.controller;

import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User created = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @DeleteMapping
    public void deleteUser(User user){
        userService.deleteUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable UUID id, @RequestBody User user){
        User edited = userService.editUser(user);
        return ResponseEntity.ok(edited);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> userInfo(Long id){
        userService.userInfo(id);
        return ResponseEntity.noContent().build();
    }

}
