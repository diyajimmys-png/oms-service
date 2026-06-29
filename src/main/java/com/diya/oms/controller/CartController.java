package com.diya.oms.controller;

import com.diya.oms.domain.Cart;
import com.diya.oms.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Cart cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addItem(@PathVariable String productId, @RequestParam int quantity) {
        cartService.addItem(productId, quantity);
        return ResponseEntity.ok("Added product"+productId +"to cart");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteItem(@PathVariable String productId) {
        cartService.removeItem(productId);
        return ResponseEntity.ok("Item Removed");
    }
}
