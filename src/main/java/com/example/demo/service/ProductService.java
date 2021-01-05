package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public ResponseEntity<Product> addProduct(Product product) {

        try {
            log.debug("Add Product : " + product.toString());
            if(product.getPrice()==null)
                product.setPrice(0.0F);
            return ResponseEntity.ok(productRepo.save(product));

        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException saving product " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error("Internal Server error Saving product " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Optional<Product>> getProductById(Long id) {

        try {
            log.debug("getProductById : " + id);
            return ResponseEntity.ok(productRepo.findById(id));
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Not Found Exception " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Exception getProdById " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   public ResponseEntity<List<Product>> getAllProducts() {
        try {
            log.debug("getAllProducts ");
            return ResponseEntity.ok(productRepo.findAll());

        } catch (Exception e) {
            log.error("Exception in getAllProducts " + e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public  ResponseEntity<Message> deleteProductById(Long id) {
        try {
            productRepo.deleteById(id);
            return ResponseEntity.ok(new Message("Product tDeleted"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
