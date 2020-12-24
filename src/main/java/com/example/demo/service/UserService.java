package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
@Autowired
    private UserRepo userRepo;
public ResponseEntity<List<User>> getAllUser(){
    return ResponseEntity.ok(userRepo.findAll());
}
public ResponseEntity<User> add(User user){
    return ResponseEntity.status(HttpStatus.CREATED).body(userRepo.save(user));
}
public  ResponseEntity<User> getByEmailId(String email )
{
    return  ResponseEntity.ok(userRepo.findByEmailContainingIgnoreCase(email));
}

public ResponseEntity<User> addUser(User user)
{
    return ResponseEntity.ok(userRepo.save(user));
}
public ResponseEntity<List<User>> getByName(String name){
    return  ResponseEntity.ok(userRepo.findByNameContainingIgnoreCase(name));
}
    public ResponseEntity<User> deleteUser(User user){
    userRepo.delete(user);
    return  ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
