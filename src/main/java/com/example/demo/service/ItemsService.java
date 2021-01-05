package com.example.demo.service;

import com.example.demo.model.Items;
import com.example.demo.repo.ItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ItemsService {
    @Autowired
    private ItemRepo itemsRepo;

    public ResponseEntity<List<Items>> getAll() {
        try {
            log.debug("Fetching all items");
            return ResponseEntity.status(HttpStatus.OK).body(itemsRepo.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    public ResponseEntity<Items> findById(Long id) {
        try {
            log.debug("Fetching item with id {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(itemsRepo.findById(id).get());
        } catch (IllegalArgumentException e) {
            log.error(e.getLocalizedMessage() + " item not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Items> add(Items items) {
        try {
            log.debug("Adding items {}", items.toString());
            return ResponseEntity.ok(itemsRepo.save(items));
        } catch (DataIntegrityViolationException e) {
            log.error("{} Duplicate name", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<Items> update(Items items) {
        try {
            log.debug("Updating items {}", items.toString());
            return ResponseEntity.ok(itemsRepo.save(items));
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<Items> delete(Items items) {
        try {
            log.debug("Deleting items {}", items.toString());
            itemsRepo.delete(items);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
