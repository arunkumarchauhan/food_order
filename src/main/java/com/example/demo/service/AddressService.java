package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repo.AddressRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    UserService userService;
    public ResponseEntity<Address> getAddressById(Long id){
        try {
            log.debug("getAddressById {}",id.toString());
            return ResponseEntity.status(200).body(addressRepo.findById(id).get());
        }
        catch (Exception e) {
            log.error("Exception in getAddressBy Id {}",e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
        }
    public ResponseEntity<List<Address>> getAddressByUserId(Long userId){
        try {

            log.debug("getAddressByUserIs {} ",userId);
            return ResponseEntity.status(200).body(userService.findUserById(userId).getBody().getAddressList());
        }
        catch (Exception e)
        {
            log.error("Exception in getAddressByUserId {}",e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
