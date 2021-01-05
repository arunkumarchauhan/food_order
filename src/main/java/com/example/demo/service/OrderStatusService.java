package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.model.OrderStatus;
import com.example.demo.repo.OrderStatusRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OrderStatusService {



    Set<String> statusSet = new HashSet<>(Arrays.asList("accepted", "cooking",
            "dispatched","arrived", "delivered","expired","rejected"));


   @Autowired
   private OrderStatusRepo orderStatusRepo;
   @Autowired
   private OrderService orderService;
    public ResponseEntity<List<OrderStatus>> getAll() {

        try {
            log.debug("Fetching all order statuses");
            return ResponseEntity.status(HttpStatus.OK).body(orderStatusRepo.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<OrderStatus> findById(Long id) {
        try {
            log.debug("Fetching order status with id {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(orderStatusRepo.findById(id).get());
        } catch (IllegalArgumentException e) {
            log.error("{} order status not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<OrderStatus>> findByStatus(String status) {
        try {
            log.debug("Fetching order status with status {}", status);
            System.out.println("Fetching order status with status "+ status);
            return ResponseEntity.status(HttpStatus.OK).body(orderStatusRepo.findByStatus(status));
        } catch (IllegalArgumentException e) {
            log.error("{} order status not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<OrderStatus> add(OrderStatus orderStatus) {
        try {
            log.debug("Adding order status {}", orderStatus.toString());
            return ResponseEntity.ok(orderStatusRepo.save(orderStatus));
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<Object> updateStatusAdmin(OrderStatus orderStatus) {
        try {
            log.debug("Updating order status {}", orderStatus.toString());
            String status=orderStatusRepo.findById(orderStatus.getId()).get().getStatus();
            System.out.println(!status.equalsIgnoreCase("expired"));
            if(statusSet.contains(orderStatus.getStatus())&&
                    !status.equalsIgnoreCase("expired")&&
            !status.equalsIgnoreCase("cancelled")&&
            !status.equalsIgnoreCase("rejected"))
          return ResponseEntity.status(200).body(orderStatusRepo.save(orderStatus));
            else throw new Exception("Invalid state in update request");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }
    public ResponseEntity<Object> updateStatusCustomer(OrderStatus orderStatus) {
        try {
            log.debug("Updating order status {}", orderStatus.toString());
            String status=orderStatusRepo.findById(orderStatus.getId()).get().getStatus();

            if(orderStatus.getStatus().equalsIgnoreCase("cancelled")&&
                    !status.equalsIgnoreCase("expired")&&
                    !status.equalsIgnoreCase("cancelled")&&
                    !status.equalsIgnoreCase("rejected"))
                return ResponseEntity.status(200).body(orderStatusRepo.save(orderStatus));
            else throw new Exception("Invalid state in update request");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    public ResponseEntity<OrderStatus> delete(Long id) {
        try {
            log.debug("Deleting order status {}",id);
            orderStatusRepo.deleteById(id);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

}
