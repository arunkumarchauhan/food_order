package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.model.Items;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Orders;
import com.example.demo.repo.ItemRepo;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderStatusService orderStatusService;
    public ResponseEntity<List<Orders>> getAllOrders(){
        try {
            log.debug("getAllOrders ");
            return  ResponseEntity.ok(orderRepo.findAll());
        }
        catch (Exception e){
            log.error("Exception in getAllOrders "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<Optional<Orders>> getOrderById(Long id){
        try {
            log.debug("getOrderById "+id);
            return  ResponseEntity.ok(orderRepo.findById(id));
        }
        catch (Exception e){
            log.error("Exception in getOrderById "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    public ResponseEntity<Object> addOrUpdateOrders(Orders order,boolean update){
        try {
            log.debug("addOrUpdateOrder "+order.toString());
            if(order.getOrderStatus().getStatus()==null) {
                {   OrderStatus orderStatus=new OrderStatus();
                    orderStatus.setStatus("placed");
                    order.setOrderStatus(orderStatus);
                }
            }
            else if(update){
                throw new Exception("Invalid update Request");
            }

            List<Items> itemsList=new ArrayList<Items>() ;
            for (Items item:order.getItemsList()) {

                long productId=-1;

                if(item.getProduct().getId()==null)
                    throw  new Exception("Product Not Found");
                else productId=item.getProduct().getId();
                    if(productRepo.findById(productId)==null)
                        throw  new Exception("Product Not Found");
                    item.setProduct(productRepo.findById(productId).get());
                    itemsList.add(item);
            }
            order.setItemsList(itemsList);
            order.setAddress(addressService.getAddressById(order.getAddress().getId()).getBody());
            order.setUser(userService.findUserById(order.getUser().getId()).getBody());

            return  ResponseEntity.status(HttpStatus.CREATED).body(orderRepo.save(order));

        }
        catch (DataIntegrityViolationException e){
            log.error(" addOrUpdateOrder DataIntegrityViolationException "+e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        catch (Exception e)
        {

            log.error("Exception in addOrUpdateOrder "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(new Message(e.getLocalizedMessage()));
        }
    }

    public ResponseEntity<Message> deleteOrderById(Long id){
         try {
             log.debug("DeleteOrderById "+id);
             orderRepo.deleteById(id);
             return ResponseEntity.ok(new Message("Order Deleted Successfully"));
         }
         catch (Exception e){
             log.error("DeleteOrderById Exception "+e.getLocalizedMessage());
             return ResponseEntity.status(500).body(null);
         }
    }
public ResponseEntity<List<Orders>> findByUserId(Long userId){
        try {
            log.debug("FindOrderByUserId "+userId);
            return ResponseEntity.status(200).body(orderRepo.findByUser(userService.findUserById(userId).getBody()));
        }
        catch (Exception e){
            log.error("Exception in findOrderByUserId "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
}

public  ResponseEntity<Orders> updateStatus(Long orderId,Long statusId){
        try {
            log.debug("updateStatus orderId : "+orderId+" statusId : "+statusId);
            Orders orders=orderRepo.findById(orderId).get();
            orders.setOrderStatus(orderStatusService.findById(statusId).getBody());

            return ResponseEntity.status(200).body(orderRepo.save(orders));
        }
        catch (Exception e){
            log.error("Exception in updateStatus "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }

}
    public ResponseEntity<List<Orders>> getAllOrdersByUserId(Long userId){
        try {
            log.debug("getAllOrders ");
            return  ResponseEntity.ok(orderRepo.findByUser(userService.findUserById(userId).getBody()));
        }
        catch (Exception e){
            log.error("Exception in getAllOrders "+e.getLocalizedMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
