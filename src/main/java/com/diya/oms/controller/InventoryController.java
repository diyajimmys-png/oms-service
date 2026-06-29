package com.diya.oms.controller;

import com.diya.oms.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addInventory(@PathVariable String productId, @RequestParam int quantity) {
        inventoryService.addStock(productId, quantity);
        return ResponseEntity.ok("Added product " + productId + " of quantity " + quantity + " to inventory");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<String> getInventory(@PathVariable String productId) {
        int stock = inventoryService.getStock(productId);
        return ResponseEntity.ok("Stock of Product " + productId + " is " + stock);
    }
}
