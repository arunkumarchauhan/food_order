package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repo.AddressRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.configs.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepo addressRepo;

    public ResponseEntity<List<User>> getAllUser() {
        try {
            log.debug("Fetching all Users");
            return ResponseEntity.ok(userRepo.findAll());
        } catch (Exception e) {
            log.error("Error Fetching All Users");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public ResponseEntity<User> findUserByToken(String token) {
        try {
            log.debug("Fetching user with token {}", token);
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findByEmailContainingIgnoreCase(jwtTokenUtil.getUsernameFromToken(token.substring(7))));
        } catch (IllegalArgumentException e) {
            log.error(e.getLocalizedMessage() + " User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<User> updateUser(User user) {
        try {
            log.debug("Updating User " + user.toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.save(user));

        } catch (Exception e) {
            log.error("Internal server Error Adding user " + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<User> getByEmailId(String email) {
        return ResponseEntity.ok(userRepo.findByEmailContainingIgnoreCase(email));
    }

    public ResponseEntity<User> addUser(User user) {
        try {
            log.debug("Adding User " + user.toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepo.save(user));

        } catch (DataIntegrityViolationException e) {
            log.error("Data Integrity Violation adding new User " + e.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error("Internal server Error Adding user " + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    public ResponseEntity<List<User>> getByName(String name) {
        try {
            log.debug("get By name " + name);
            return ResponseEntity.ok(userRepo.findByNameContainingIgnoreCase(name));

        } catch (Exception e) {
            log.error("Exception in find by name " + e.toString());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<Message> deleteUserById(Long id) {
        try {
            log.debug("Delete User by  id");
            userRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("User deleted"));
        } catch (Exception e) {
            log.error("Error deleting user " + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Error deleting User"));
        }
    }

    public ResponseEntity<User> findUserById(Long id) {
        try {
            log.debug("Fetching user by id {}", id);
            return ResponseEntity.ok(userRepo.findById(id).get());

        } catch (IllegalArgumentException e) {
            log.error(e.getLocalizedMessage() + " User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Error FInd user by id ", e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<User> addAddress(Address newAddress, String jwtToken) {
        try {
            log.debug("Adding addAddress {}", newAddress.toString());
            User users = findUserByToken(jwtToken).getBody();
            if (users == null) {
                return ResponseEntity.status(404).body(null);
            }
            //Assert only one primary address. If one, make it primary
            List<Address> addressList = users.getAddressList();
            if (addressList.isEmpty()) {
                newAddress.setIsPrimaryAddress(true);
                newAddress = addressRepo.save(newAddress);
                addressList.add(newAddress);
                users.setAddressList(addressList);
            } else {
                if (Boolean.TRUE.equals(newAddress.getIsPrimaryAddress())) {
                    for (Address add : addressList
                    ) {
                        add.setIsPrimaryAddress(false);
                        addressRepo.save(add);
                    }
                    newAddress = addressRepo.save(newAddress);
                    addressList.add(newAddress);
                    users.setAddressList(addressList);
                }

            }
            return ResponseEntity.ok(userRepo.save(users));
        } catch (DataIntegrityViolationException e) {
            log.error("{} Duplicate email", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}


