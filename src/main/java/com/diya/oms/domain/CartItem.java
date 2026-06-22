package com.diya.oms.domain;

import java.math.BigDecimal;

public class CartItem {

    private final Product product;   // final — never changes after creation
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {                          // 0 is also invalid
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.product  = product;
        this.quantity = quantity;
    }

    public Product getProduct()  { return product; }
    public int getQuantity()     { return quantity; }

    public void increaseQuantity(int by) {            // void — it's a mutator
        if (by <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        quantity += by;
    }

    public BigDecimal getLineTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "CartItem{product='%s', qty=%d, lineTotal=%s}"
                .formatted(product.getName(), quantity, getLineTotal());
    }
}
