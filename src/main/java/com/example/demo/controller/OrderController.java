package com.example.demo.controller;


import com.example.demo.dto.Message;
import com.example.demo.model.Orders;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order/")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("all")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("all/by_userId/{id}")
    public ResponseEntity<List<Orders>> getAllOrderByUserId(@PathVariable Long id) {
        return orderService.getAllOrdersByUserId(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Orders>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteOrderById(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

    @PostMapping("add")
    public ResponseEntity<Object> createOrder(@RequestBody Orders order) {
        return orderService.addOrUpdateOrders(order,false);
    }
    @PostMapping("update")
    public ResponseEntity<Object> updateOrder(@RequestBody Orders order) {
        return orderService.addOrUpdateOrders(order,true);
    }

}
