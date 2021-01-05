package com.example.demo.repo;

import com.example.demo.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusRepo extends JpaRepository<OrderStatus,Long> {
    List<OrderStatus> findByStatus(String status);
}
