package com.example.demo.repo;

import com.example.demo.model.Orders;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    public List<Orders> findByUser(User user);
}
