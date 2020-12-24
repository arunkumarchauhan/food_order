package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin("*")
public class ProductController {
    private ProductRepo productRepo;
    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") Long id){
      return   ResponseEntity.ok(productRepo.findById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }
    @PostMapping("")
    public ResponseEntity<Product> addProduct(Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepo.save(product));
    }
}
