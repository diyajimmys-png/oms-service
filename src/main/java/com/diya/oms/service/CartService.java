package com.diya.oms.service;

import com.diya.oms.domain.Cart;
import com.diya.oms.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final Cart cart;
    private final ProductService productService;

    public CartService(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    public Cart getCart(){
        return cart;
    }
     public void addItem(String productId, int quantity){
         try {
             Product p = productService.getProductById(productId);
             cart.addItem(p, quantity);
         } catch (RuntimeException e) {
             throw new IllegalArgumentException("Product not found: " + productId);
         }
     }

     public void removeItem(String productId){
       cart.removeItem(productId);
     }
}
