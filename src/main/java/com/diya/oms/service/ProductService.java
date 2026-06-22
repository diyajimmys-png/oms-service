package com.diya.oms.service;

import com.diya.oms.domain.Product;
import com.diya.oms.domain.enums.Category;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> productList;

    public ProductService() {
        Product laptop = new Product("Laptop", new BigDecimal("200.00"), Category.ELECTRONICS, "15 inch");
        Product book = new Product("Clean Code", new BigDecimal("35.00"), Category.BOOKS, "By Robert Martin");
        Product shirt = new Product("T-Shirt", new BigDecimal("20.00"), Category.CLOTHING, "Cotton");
        this.productList = new ArrayList<>(List.of(laptop, book, shirt));
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProductById(String id) {
        return productList.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product addProduct(Product p){
        productList.add(p);
        return p;
    }

}
