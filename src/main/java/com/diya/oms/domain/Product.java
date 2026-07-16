package com.diya.oms.domain;

import com.diya.oms.domain.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a product in the catalogue.
 *
 * Design note: immutable after creation — price and stock
 * are managed by InventoryService (Sprint 2), not mutated directly here.
 */
@Entity
public class Product {

    @Id
    private final String id;          // UUID string — no int IDs in enterprise systems
    private final String name;
    private final BigDecimal price;   // BigDecimal, never double/float for money
    @Enumerated(EnumType.STRING)
    private final Category category;
    private final String description;

    public Product(String id, String name, BigDecimal price, Category category, String description) {
        // Guard clauses — validate on construction, fail fast
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        this.id = id;
        this.name        = name;
        this.price       = price;
        this.category    = category;
        this.description = description;
    }

    protected Product(){
        this.name = null;
        this.price = null;
        this.category = null;
        this.description = null;
        this.id = null;
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
