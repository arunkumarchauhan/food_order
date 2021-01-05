package com.example.demo.controller;


import com.example.demo.model.OrderStatus;
import com.example.demo.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order/status/")
@CrossOrigin("*")
public class OrderStatusController {
    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping("all")
    public ResponseEntity<List<OrderStatus>> getAllStatus() {
        return orderStatusService.getAll();
    }

    @GetMapping("by_id/{id}")
    public ResponseEntity<OrderStatus> getAllStatus(@PathVariable Long id) {
        return orderStatusService.findById(id);
    }

    @GetMapping("by_status/{status}")
    public ResponseEntity<List<OrderStatus>> getAllStatus(@PathVariable String status) {
        return orderStatusService.findByStatus(status);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OrderStatus> deleteStatus(@PathVariable Long id) {
        return orderStatusService.delete(id);
    }

    @PutMapping("user/update")
    public ResponseEntity<Object> updateByUser(@RequestBody OrderStatus orderStatus) {
        return orderStatusService.updateStatusCustomer(orderStatus);

    }

    @PutMapping("admin/update")
    public ResponseEntity<Object> updateByAdmin(@RequestBody OrderStatus orderStatus) {
        return orderStatusService.updateStatusAdmin(orderStatus);
    }
}
