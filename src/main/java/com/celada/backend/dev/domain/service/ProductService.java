package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class ProductService {

    private final ExistingProductRepository existingProductRepository;

    public ProductService(ExistingProductRepository existingProductRepository) {
        this.existingProductRepository = existingProductRepository;
    }

    public Set<Product> getProductSimilar(String productId) {
        if (productId == null) return Collections.emptySet();
        return existingProductRepository.getSimilarProducts(productId);
    }
}
