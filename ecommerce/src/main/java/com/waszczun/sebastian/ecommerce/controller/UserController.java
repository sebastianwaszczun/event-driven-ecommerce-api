package com.waszczun.sebastian.ecommerce.controller;

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
    public ResponseEntity<Void> deleteUser(Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user){
        User edited = userService.editUser(user);
        return ResponseEntity.ok(edited);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> userInfo(@PathVariable Long id){
        return ResponseEntity.ok(userService.userInfo(id));
    }

}
