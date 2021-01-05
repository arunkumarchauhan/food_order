package com.example.demo.controller;

import com.example.demo.model.Items;
import com.example.demo.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item/")
@CrossOrigin("*")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @GetMapping("all")
    public ResponseEntity<List<Items>> getAll() {
        return itemsService.getAll();
    }


    @GetMapping("by_id")
    public ResponseEntity<Items> getById(@RequestParam Long id) {
        return itemsService.findById(id);
    }

    @PostMapping("add")
    public ResponseEntity<Items> add(@RequestBody Items items) {
        return itemsService.add(items);
    }

    @PutMapping("update")
    public ResponseEntity<Items> update(@RequestBody Items items) {
        return itemsService.update(items);
    }

    @PostMapping("delete")
    public ResponseEntity<Items> delete(@RequestBody Items items) {
        return itemsService.delete(items);
    }
}
