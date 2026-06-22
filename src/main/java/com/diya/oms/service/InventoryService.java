package com.diya.oms.service;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<String, Integer> stock;

    public InventoryService() {
        this.stock = new HashMap<>();
    }

    public void addStock(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Add quantity must be positive");
        }
        stock.merge(productId,quantity, Integer::sum);
        //if (stock.containsKey(productId)) {
        //    stock.put(productId, stock.get(productId) + quantity);
        //} else {
        //    stock.put(productId, quantity);
        //}stock.merge(productId, quantity, (oldValue, newValue) -> oldValue + newValue);
    }

    public boolean reduceStock(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Decrease quantity must be positive");
        }
        if (!stock.containsKey(productId)) {
            return false;
        }
        int currentStock = stock.get(productId);
        int newStock = currentStock - quantity;
        if (newStock >= 0) {
            stock.put(productId, newStock);
            return true;
        }
        return false;


    }

    public int getStock(String productId) {
        if (!stock.containsKey(productId)) {
            throw new IllegalArgumentException("Product ID not found");
        }
        return stock.get(productId);
    }

    public boolean isAvailable(String productId, int quantity) {
        if (!stock.containsKey(productId) || quantity <= 0) {
            return false;
        }
        return getStock(productId) >= quantity;
    }
}
