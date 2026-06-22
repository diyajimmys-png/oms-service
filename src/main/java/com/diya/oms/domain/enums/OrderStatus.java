package com.diya.oms.domain.enums;

public enum OrderStatus {
    PENDING,       // order placed, payment not yet confirmed
    CONFIRMED,     // payment confirmed
    SHIPPED,       // dispatched from warehouse
    DELIVERED,     // reached customer
    CANCELLED      // cancelled before shipping
}
