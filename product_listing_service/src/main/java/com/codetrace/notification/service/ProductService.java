package com.codetrace.notification.service;

import com.codetrace.notification.domain.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> productRepo = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepo.values());
    }

    public Product getProductById(Long id) {
        return productRepo.get(id);
    }

    public Product addProduct(Product product) {
        long id = idGenerator.getAndIncrement();
        product.setId(id);
        productRepo.put(id, product);
        return product;
    }
}

