package com.waszczun.sebastian.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waszczun.sebastian.ecommerce.config.SecurityConfig;
import com.waszczun.sebastian.ecommerce.dto.UserRequest;
import com.waszczun.sebastian.ecommerce.dto.UserResponse;
import com.waszczun.sebastian.ecommerce.mapper.UserMapper;
import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserMapper userMapper;

    @Test
    void userInfo_ShouldReturnUser_WhenUserExists() throws Exception{
        //GIVEN
        long userId = 1L;
        User user = new User(userId, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        UserResponse userResponse = new UserResponse(userId, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789");
        when(userService.userInfo(userId)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        //THEN
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Adam"));
    }

    @Test
    void userInfo_ShouldReturnException_WhenUserDoesntExists() throws Exception{
        //GIVEN
        long userId = 1L;
        when(userService.userInfo(userId)).thenReturn(null);

        //THEN
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("User not found"));

    }

    @Test
    void getUsers_ShouldReturnAllUsers() throws Exception{
        //GIVEN
        User user = new User(1, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        List<User> userList = List.of(user);
        UserResponse userResponse = new UserResponse(1, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789");
        List<UserResponse> responseList = List.of(userResponse);
        when(userService.getUsers()).thenReturn(userList);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        //THEN
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(responseList.getFirst().firstName()))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() throws Exception{
        //GIVEN
        long userId = 1L;

        //THEN
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void editUser_ShouldReturnUpdatedUser() throws Exception{
        //GIVEN
        long userId = 1L;
        UserRequest userRequest = new UserRequest("Adam", "Nowak", "123456789");
        User user = new User(userId, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        User user1 = new User(userId, "Adrian", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        UserResponse userResponse = new UserResponse(1, "Adrian", "Nowak", "adam.nowak@gmail.com", "123456789");
        when(userService.userInfo(userId)).thenReturn(user);
        when(userService.updateUser(user)).thenReturn(user1);
        when(userMapper.toResponse(user1)).thenReturn(userResponse);

        //THEN
        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Adrian"))
                .andExpect(jsonPath("$.lastName").value("Nowak"));


    }

}
