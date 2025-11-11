package com.waszczun.sebastian.ecommerce.service;

import com.waszczun.sebastian.ecommerce.model.User;
import com.waszczun.sebastian.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public User editUser(User user){
        Optional<User> byId = userRepository.findById(user.getId());
        byId.get().setFirstName(user.getFirstName());
        byId.get().setLastName(user.getLastName());
        byId.get().setEmail(user.getEmail());
        return byId.get();
    }

    public User userInfo(Long id){
        Optional<User> byId = userRepository.findById(id);
        return byId.get();
    }


}
