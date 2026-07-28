package com.diya.oms.domain;

import com.diya.oms.domain.enums.Category;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void addingProductTwiceIncreasesQuantityNotLineCount() {
        Product phone = new Product(UUID.randomUUID().toString(), "Phone", new BigDecimal("599.99"), Category.ELECTRONICS, "Smartphone");
        Cart cart = new Cart();
        cart.addItem(phone, 1);
        cart.addItem(phone, 1);
        assertEquals(1, cart.getItemCount());
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    void totalIsCorrect() {
        Product book  = new Product(UUID.randomUUID().toString(), "Clean Code", new BigDecimal("35.00"), Category.BOOKS, "By Robert Martin");
        Product shirt = new Product(UUID.randomUUID().toString(), "T-Shirt", new BigDecimal("20.00"), Category.CLOTHING, "Cotton");
        Cart cart = new Cart();
        cart.addItem(book,  2);
        cart.addItem(shirt, 3);
        assertEquals(new BigDecimal("130.00"), cart.getTotal());
    }

    @Test
    void removeItemLeavesCartWithoutThatProduct() {
        Product laptop = new Product(UUID.randomUUID().toString(), "Laptop", new BigDecimal("999.00"), Category.ELECTRONICS, "15 inch");
        Cart cart = new Cart();
        cart.addItem(laptop, 1);
        cart.removeItem(laptop.getId());
        assertTrue(cart.isEmpty());
    }

    @Test
    void orderCannotBeCreatedFromEmptyCart() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(UUID.randomUUID().toString(), "customer-004", List.of())
        );
    }
}
