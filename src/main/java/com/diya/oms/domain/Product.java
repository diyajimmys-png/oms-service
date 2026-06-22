package com.diya.oms.domain;

import com.diya.oms.domain.enums.Category;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a product in the catalogue.
 *
 * Design note: immutable after creation — price and stock
 * are managed by InventoryService (Sprint 2), not mutated directly here.
 */
public class Product {

    private final String id;          // UUID string — no int IDs in enterprise systems
    private final String name;
    private final BigDecimal price;   // BigDecimal, never double/float for money
    private final Category category;
    private final String description;

    public Product(String name, BigDecimal price, Category category, String description) {
        // Guard clauses — validate on construction, fail fast
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        this.id          = UUID.randomUUID().toString();
        this.name        = name;
        this.price       = price;
        this.category    = category;
        this.description = description;
    }

    // --- Getters (no setters — immutable after creation) ---

    public String getId()          { return id; }
    public String getName()        { return name; }
    public BigDecimal getPrice()   { return price; }
    public Category getCategory()  { return category; }
    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product p)) return false;
        return Objects.equals(id, p.id);   // equality by ID, not name
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{id='%s', name='%s', price=%s, category=%s}"
                .formatted(id, name, price, category);
    }
}
