package com.diya.oms.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A shopping cart belonging to one customer.
 *
 * KEY DESIGN DECISIONS:
 *
 * 1. Composition: Cart HAS-A list of CartItems. Not Cart extends List.
 *    Extending List would expose 30+ methods we don't want (add(index, item), etc.)
 *    This is the classic "favour composition over inheritance" example.
 *
 * 2. ArrayList chosen deliberately: we need index-based access and
 *    fast iteration. LinkedList would be worse here (cache-unfriendly).
 *
 * 3. Returns unmodifiableList to callers — internal state is not leaked.
 */
//Using @Component on a domain object is acceptable for a single-user in-memory application like Sprint 3.
// In a real multi-user application, one shared Cart would be a serious bug — every user would share the same cart.
// Sprint 7 onwards with PostgreSQL and Spring Security, Cart becomes a database entity tied to a user session.
@Component
public class Cart {

    private String customerId;
    private final List<CartItem> items;   // ArrayList internally

    public Cart() { // no-arg constructor for Spring
        this.customerId = "guest";
        this.items = new ArrayList<>();
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Add a product to the cart.
     * If the product already exists, increases its quantity instead of
     * adding a duplicate line — same behaviour as Hybris cart.
     */
    public void addItem(Product product, int quantity) {
        Optional<CartItem> existing = findItem(product.getId());

        if (existing.isPresent()) {
            existing.get().increaseQuantity(quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    /**
     * Remove a product from the cart entirely.
     */
    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    /**
     * Cart grand total — sum of all line totals.
     * BigDecimal.ZERO → the starting value (identity).
     * BigDecimal::add → the function used to combine values.
     * reduce is the terminal function
     */
    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.size();
    }

    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns an unmodifiable view — callers cannot mutate internal list directly.
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clearItems() {
        items.clear(); // ✅ clears the internal list directly
    }

    // Internal helper — not exposed publicly
    private Optional<CartItem> findItem(String productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    @Override
    public String toString() {
        return "Cart{customerId='%s', items=%d, total=%s}"
                .formatted(customerId, items.size(), getTotal());
    }
}
