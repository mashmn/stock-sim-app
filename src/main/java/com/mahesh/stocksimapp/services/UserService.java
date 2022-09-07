package com.mahesh.stocksimapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahesh.stocksimapp.exceptions.ResourceNotFoundException;
import com.mahesh.stocksimapp.models.User;
import com.mahesh.stocksimapp.repository.UserRepository;

import lombok.AllArgsConstructor;

// @AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found " + userId)
        );
    }

    public List<User> addUsers(List<User> users){
        return userRepository.saveAll(users);
    }
    
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
