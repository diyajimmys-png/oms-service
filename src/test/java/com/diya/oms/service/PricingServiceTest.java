package com.diya.oms.service;

import com.diya.oms.domain.Product;
import com.diya.oms.domain.enums.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PricingServiceTest {
    private PricingService pricingService;

    @BeforeEach
    void setup() {
        pricingService = new PricingService();
    }

    @Test
    void getTier_midRangePrice_returnsStandard(){
        assertEquals("Standard",pricingService.getTier(new BigDecimal("75.00")));
    }

    @Test
    void getTier_exactBoundaryPrice_returnsPremium(){
        assertEquals("Premium",pricingService.getTier(new BigDecimal("200.00")));
    }
    @Test
    void getTier_negativePrice_throwsException(){
        assertThrows(IllegalArgumentException.class,() -> pricingService.getTier(new BigDecimal("-20.00")));
    }

    @Test
    void getSortedProducts_returnsAscendingOrder (){
        Product laptop = new Product(UUID.randomUUID().toString(), "Laptop", new BigDecimal("200.00"), Category.ELECTRONICS, "15 inch");
        Product book   = new Product(UUID.randomUUID().toString(), "Clean Code", new BigDecimal("35.00"), Category.BOOKS, "By Robert Martin");
        Product shirt  = new Product(UUID.randomUUID().toString(), "T-Shirt", new BigDecimal("20.00"), Category.CLOTHING, "Cotton");
        List<Product> products = List.of(laptop,book,shirt);
        List<Product> sorted = pricingService.getSortedProducts(products);
        assertEquals(new BigDecimal("20.00"),sorted.get(0).getPrice());
    }
}
