package com.diya.oms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id
    private final String id;          // UUID string — no int IDs in enterprise systems
    private final String productId;
    private final int quantity;
    private final BigDecimal price;

    public OrderItem(String id, String productId, int quantity, BigDecimal price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    protected OrderItem() {
        this.id = null;
        this.productId = null;
        this.quantity = 0;
        this.price = null;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
