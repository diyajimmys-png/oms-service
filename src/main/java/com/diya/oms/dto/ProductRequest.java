package com.diya.oms.dto;

import com.diya.oms.domain.enums.Category;
import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Category category;
    private String description;

    // no-arg constructor needed for Jackson deserialisation
    public ProductRequest() {}

    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Category getCategory() { return category; }
    public String getDescription() { return description; }
}
