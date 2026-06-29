package com.diya.oms.service;

import com.diya.oms.domain.Cart;
import com.diya.oms.domain.CartItem;
import com.diya.oms.domain.Order;
import com.diya.oms.payment.PaymentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CheckoutService {
    private final Cart cart;
    private final InventoryService inventoryService;
    private final PaymentStrategy paymentStrategy;
    private final OrderService orderService;

    public CheckoutService(Cart cart, InventoryService inventoryService, PaymentStrategy paymentStrategy, OrderService orderService) {
        this.cart = cart;
        this.inventoryService = inventoryService;
        this.paymentStrategy = paymentStrategy;
        this.orderService = orderService;
    }

    @Transactional
    public Order checkout() {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot checkout from an empty cart");
        }
        for (CartItem item : cart.getItems()) {
            String productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            if (!inventoryService.isAvailable(productId, quantity)) {
                throw new IllegalStateException(String.format("Stock is unavailable for productId %s quantity %d ", productId, quantity));
            }
        }
        for (CartItem item : cart.getItems()) {
            String productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            inventoryService.reduceStock(productId, quantity);
        }
        Order order = new Order(cart.getCustomerId(), new ArrayList<>(cart.getItems()));
        paymentStrategy.pay(order.getTotalAmount());
        orderService.placeOrder(order);
        cart.clearItems();
        return order;
    }
}
