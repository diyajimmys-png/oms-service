package com.diya.oms.service;

import com.diya.oms.domain.Product;
import com.diya.oms.dto.ProductRequest;
import com.diya.oms.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product addProduct(ProductRequest request) {
        Product p = new Product(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getDescription()
        );
        return productRepository.save(p);
    }

}
