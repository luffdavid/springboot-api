package com.financemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financemanagement.service.UserService;
import com.financemanagement.model.User;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "https://mefinance.vercel.app" })
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;

    // Signup
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            if (service.doesEmailExist(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
            }
            User createdUser = service.signupUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            User existingUser = service.getUserByEmail(user.getEmail());
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }
            if (!existingUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
