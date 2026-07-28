package com.diya.oms.domain;

import com.diya.oms.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    private final String orderId;
    private final String customerId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime placedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String orderId, String customerId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cannot create an order from an empty cart");
        }
        this.orderId     = orderId;
        this.customerId  = customerId;
        this.items       = Collections.unmodifiableList(items);
        this.totalAmount = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.placedAt    = LocalDateTime.now();
        this.status      = OrderStatus.PENDING;
    }

    protected Order() {
        this.orderId     = null;
        this.customerId  = null;
        this.items       = null;
        this.totalAmount = null;
        this.placedAt    = null;
        this.status      = null;
    }

    public void updateStatus(OrderStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: %s → %s".formatted(this.status, newStatus)
            );
        }
        this.status = newStatus;
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PENDING   -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.SHIPPED   || next == OrderStatus.CANCELLED;
            case SHIPPED   -> next == OrderStatus.DELIVERED;
            default        -> false;
        };
    }

    public String getOrderId()         { return orderId; }
    public String getCustomerId()      { return customerId; }
    public List<OrderItem> getItems()  { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getPlacedAt() { return placedAt; }
    public OrderStatus getStatus()     { return status; }

    @Override
    public String toString() {
        return "Order{id='%s', customer='%s', total=%s, status=%s, placedAt=%s}"
                .formatted(orderId, customerId, totalAmount, status, placedAt);
    }
}
