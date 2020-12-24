package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/user/")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("by_name")
    public ResponseEntity<List<User>> getUserByName(@RequestParam String name) {
        return userService.getByName(name);
    }

    @GetMapping("by_email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userService.getByEmailId(email);
    }

    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }
}
