package com.diya.oms.domain;

import com.diya.oms.domain.enums.Category;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void addingProductTwiceIncreasesQuantityNotLineCount() {
        Product phone = new Product("Phone", new BigDecimal("599.99"), Category.ELECTRONICS, "Smartphone");
        Cart cart = new Cart("customer-001");

        cart.addItem(phone, 1);
        cart.addItem(phone, 1);   // same product, second time

        assertEquals(1, cart.getItemCount());   // still 1 line, not 2
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    void totalIsCorrect() {
        Product book  = new Product("Clean Code", new BigDecimal("35.00"), Category.BOOKS, "By Robert Martin");
        Product shirt = new Product("T-Shirt",    new BigDecimal("20.00"), Category.CLOTHING, "Cotton");
        Cart cart = new Cart("customer-002");

        cart.addItem(book,  2);   // 70.00
        cart.addItem(shirt, 3);   // 60.00

        assertEquals(new BigDecimal("130.00"), cart.getTotal());
    }

    @Test
    void removeItemLeavesCartWithoutThatProduct() {
        Product laptop = new Product("Laptop", new BigDecimal("999.00"), Category.ELECTRONICS, "15 inch");
        Cart cart = new Cart("customer-003");
        cart.addItem(laptop, 1);
        cart.removeItem(laptop.getId());

        assertTrue(cart.isEmpty());
    }

    @Test
    void orderCannotBeCreatedFromEmptyCart() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order("customer-004", java.util.List.of())
        );
    }
}
