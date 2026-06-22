package com.diya.oms.domain;

import com.diya.oms.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * An Order is created from a confirmed Cart.
 *
 * Once created, items are frozen — the order is a snapshot of what
 * was in the cart at checkout time (just like a real invoice).
 *
 * Design note: Order does not extend Cart. An Order IS NOT a Cart —
 * it's a completed record derived from one. Composition again.
 */
public class Order {

    private final String          orderId;
    private final String          customerId;
    private final List<CartItem>  items;          // snapshot — immutable after creation
    private final BigDecimal      totalAmount;
    private final LocalDateTime   placedAt;
    private       OrderStatus     status;

    /**
     * Package-private constructor — Orders are created by OrderService,
     * not directly by client code. (Sprint 3 will enforce this properly.)
     */
    public Order(String customerId, List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cannot create an order from an empty cart");
        }
        this.orderId     = UUID.randomUUID().toString();
        this.customerId  = customerId;
        this.items       = Collections.unmodifiableList(items); // freeze snapshot
        this.totalAmount = items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.placedAt    = LocalDateTime.now();
        this.status      = OrderStatus.PENDING;
    }

    /**
     * Status transitions — only valid moves allowed.
     * Prevents jumping from PENDING straight to DELIVERED.
     */
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
            default        -> false;  // DELIVERED and CANCELLED are terminal states
        };
    }

    // --- Getters ---
    public String getOrderId()            { return orderId; }
    public String getCustomerId()         { return customerId; }
    public List<CartItem> getItems()      { return items; }
    public BigDecimal getTotalAmount()    { return totalAmount; }
    public LocalDateTime getPlacedAt()    { return placedAt; }
    public OrderStatus getStatus()        { return status; }

    @Override
    public String toString() {
        return "Order{id='%s', customer='%s', total=%s, status=%s, placedAt=%s}"
                .formatted(orderId, customerId, totalAmount, status, placedAt);
    }
}
