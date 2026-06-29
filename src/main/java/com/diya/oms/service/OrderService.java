package com.diya.oms.service;

import com.diya.oms.domain.Order;
import com.diya.oms.domain.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final List<Order> orderList;

    public OrderService() {
        this.orderList = new ArrayList<>();
    }

    public void placeOrder(Order order){
        if(order != null){
            orderList.add(order);
        }
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderList.stream().filter(order -> order.getStatus().equals(status)).toList();
    }

    public BigDecimal getTotalRevenue(){
        return orderList.stream().map(Order :: getTotalAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public List<Order> getOrdersSortedByAmount(){
        return orderList.stream().sorted(Comparator.comparing(Order::getTotalAmount).reversed()).toList();
    }
}
