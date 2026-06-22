package com.diya.oms.controller;

import com.diya.oms.domain.Order;
import com.diya.oms.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<?> checkout(){
        try {
            Order o = checkoutService.checkout();
            return ResponseEntity.ok(o);
        }
        catch (IllegalStateException e)
        {
           return ResponseEntity.badRequest().body(e.getMessage()) ;
        }
    }
}
