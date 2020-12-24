package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
@Autowired
    private ProductRepo productRepo;
ResponseEntity<Product> addProduct(Product product){
    return ResponseEntity.ok(productRepo.save(product));
}

ResponseEntity<Optional<Product>> getProductById(Long id){
    return ResponseEntity.ok(productRepo.findById(id));
}

ResponseEntity<List<Product>> getAllProducts(){
    return ResponseEntity.ok(productRepo.findAll());
}
ResponseEntity<Product>updateProduct(Product product){
    return ResponseEntity.ok(productRepo.save(product));
}

}
