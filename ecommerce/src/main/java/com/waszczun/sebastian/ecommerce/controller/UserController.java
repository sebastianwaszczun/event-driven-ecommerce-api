package com.waszczun.sebastian.ecommerce.controller;

import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public User addUser(User user){
        return userService.addUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping("/user")
    public void deleteUser(User user){
        userService.deleteUser(user);
    }

    @PutMapping("/user")
    public User editUser(User user){
        return userService.editUser(user);
    }

    @GetMapping("/user/{id}")
    public User userInfo(Long id){
        return userService.userInfo(id);
    }

}
