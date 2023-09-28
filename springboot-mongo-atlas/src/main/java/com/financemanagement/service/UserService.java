package com.financemanagement.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financemanagement.model.User;
import com.financemanagement.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE NEW USER => Signup
    public User signupUser(User user) {
        user.setUserId(UUID.randomUUID().toString().split("-")[0]);
        return userRepository.save(user);
    }

    // login
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean doesEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId()).get();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public String deleteAccount(String userId) {
        userRepository.deleteById(userId);
        return userId + " Account deleted";
    }
}
