package com.diya.oms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    void addStock_shouldStoreCorrectQuantity() {
        inventoryService.addStock("prod-001", 10);
        assertEquals(10, inventoryService.getStock("prod-001"));

    }

    @Test
    void addStock_twiceToSameProduct() {
        inventoryService.addStock("prod-001", 10);
        inventoryService.addStock("prod-001", 20);
        assertEquals(30, inventoryService.getStock("prod-001"));

    }

    @Test
    void reduceStock_whenSufficientStock_returnsTrue() {
        inventoryService.addStock("prod-001", 10);
        assertTrue(inventoryService.reduceStock("prod-001", 5));
    }

    @Test
    void reduceStock_whenInsufficientStock_returnsFalse() {
        inventoryService.addStock("prod-001", 10);
        assertFalse(inventoryService.reduceStock("prod-001", 15));
    }

    @Test
    void getStock_whenProductIdNotExist(){
        //The lambda delays execution — assertThrows runs it inside a try/catch internally.
        assertThrows(IllegalArgumentException.class,()-> inventoryService.getStock("prod-002"));
    }

    @Test
    void isAvailable_whenStockIsSufficient_returnsTrue(){
        inventoryService.addStock("prod-001", 10);
        assertTrue(inventoryService.isAvailable("prod-001", 5));
    }


}
