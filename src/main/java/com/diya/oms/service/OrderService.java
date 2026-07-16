package com.diya.oms.service;

import com.diya.oms.domain.Order;
import com.diya.oms.domain.enums.OrderStatus;
import com.diya.oms.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void placeOrder(Order order){
        if(order != null){
           orderRepository.save(order);
        }
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepository.findAll().stream().filter(order -> order.getStatus().equals(status)).toList();
    }

    public BigDecimal getTotalRevenue(){
        return orderRepository.findAll().stream().map(Order :: getTotalAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public List<Order> getOrdersSortedByAmount(){
        return orderRepository.findAll().stream().sorted(Comparator.comparing(Order::getTotalAmount).reversed()).toList();
    }
}
