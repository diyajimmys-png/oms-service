package com.diya.oms.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class InventoryService {
    private final Map<String, AtomicInteger> stock;
    private final ReentrantLock lock = new ReentrantLock();

    public InventoryService() {
        this.stock = new ConcurrentHashMap<>();
    }

    public void addStock(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Add quantity must be positive");
        }
        stock.computeIfAbsent(productId, k -> new AtomicInteger(0)).addAndGet(quantity);
        //stock.merge(productId,quantity, Integer::sum);
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
        lock.lock();
        try {
            int currentStock = stock.get(productId).get();
            int newStock = currentStock - quantity;
            if (newStock >= 0) {
                stock.get(productId).set(newStock);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    public int getStock(String productId) {
        if (!stock.containsKey(productId)) {
            throw new IllegalArgumentException("Product ID not found");
        }
        return stock.get(productId).get();
    }

    public boolean isAvailable(String productId, int quantity) {
        if (!stock.containsKey(productId) || quantity <= 0) {
            return false;
        }
        return getStock(productId) >= quantity;
    }
}
