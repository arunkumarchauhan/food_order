package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.model.Address;
import com.example.demo.model.InvalidTokens;
import com.example.demo.model.User;
import com.example.demo.security.models.InvalidateTokenResponse;
import com.example.demo.service.InvalidTokensService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/user/")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
@Autowired
    InvalidTokensService invalidTokensService;
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

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @PutMapping("add_address")
    public ResponseEntity<User> add(@RequestBody Address userAddresses, @RequestHeader("Authorization") String jwtToken) {
        return userService.addAddress(userAddresses, jwtToken);
    }
    @GetMapping("by_jwt")
    public ResponseEntity<User> getByJWT(@RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }


    @PostMapping("logout")
    public ResponseEntity<?> invalidateToken(@RequestBody InvalidTokens invalidTokens) throws Exception {
        System.out.println("IN LOGOUT");

        try {
            invalidTokensService.saveToken(invalidTokens.getToken());
            return ResponseEntity.ok(new InvalidateTokenResponse("success"));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (BadCredentialsException e) {
            System.out.println("Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
