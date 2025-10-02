package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class ProductService {
    public Set<Product> getProductSimilar(String productId) {
        Set<Product> products = new HashSet<>();
        Product product = Product.builder()
                .id(productId)
                .name("Product 1")
                .price(BigDecimal.TEN)
                .build();
        products.add(product);
        return products;
    }
}
