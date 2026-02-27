package com.waszczun.sebastian.ecommerce.service;

import com.waszczun.sebastian.ecommerce.exception.ResourceNotFoundException;
import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers(){
        //GIVEN
        User user1 = new User(1, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        User user2 = new User(2, "Michal", "Nowak", "michal.nowak@gmail.com", "123456798", "mojeTajneHaslo");
        List<User> userList = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        //WHEN
        List<User> users = userService.getUsers();

        //THEN
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserByIdWhenExists(){
        //GIVEN
        User user1 = new User(1L, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        //WHEN
        User user = userService.userInfo(1L);

        //THEN
        assertEquals(user1, user);
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserByIdDoesNotExist(){
        //GIVEN
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        //THEN
        assertThrows(ResourceNotFoundException.class, () -> userService.userInfo(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateUser(){
        //GIVEN
        User user1 = new User(1L, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        when(userRepository.save(user1)).thenReturn(user1);

        //WHEN
        User user = userService.updateUser(user1);

        //THEN
        assertEquals(user1, user);
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUserWhenExists(){
        //GIVEN
        User user1 = new User(1L, "Adam", "Nowak", "adam.nowak@gmail.com", "123456789", "mojeTajneHaslo");
        when(userRepository.existsById(1L)).thenReturn(true);
        //WHEN
        userService.deleteUser(1L);

        //THEN
        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUser(){
        //GIVEN
        when(userRepository.existsById(1L)).thenReturn(false);

        //THEN
        assertThrows(ResourceNotFoundException.class, ()->userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }
}
