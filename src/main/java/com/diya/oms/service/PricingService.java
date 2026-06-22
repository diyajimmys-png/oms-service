package com.diya.oms.service;

import com.diya.oms.domain.Product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PricingService {
    private TreeMap<BigDecimal, String> priceTiers;

    public PricingService() {
        this.priceTiers = new TreeMap<>();
        priceTiers.put(new BigDecimal("0.00"), "Budget");
        priceTiers.put(new BigDecimal("50.00"), "Standard");
        priceTiers.put(new BigDecimal("200.00"), "Premium");
        priceTiers.put(new BigDecimal("500.00"), "Luxury");
    }

    //floorKey() finds the largest key less than or equal to your price
    public String getTier(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        BigDecimal priceRange = priceTiers.floorKey(price);
        if (priceRange == null) {
            throw new IllegalArgumentException("Price is below minimum tier");
        }
        return priceTiers.get(priceRange);
    }

    public List<Product> getSortedProducts(List<Product> products) {
        if (products == null) {
            throw new IllegalArgumentException("Product list is null");
        }
        return products.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
    }
}
