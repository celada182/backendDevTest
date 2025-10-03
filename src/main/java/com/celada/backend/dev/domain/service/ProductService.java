package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class ProductService {

    private final ExistingProductRepository existingProductRepository;

    public ProductService(ExistingProductRepository existingProductRepository) {
        this.existingProductRepository = existingProductRepository;
    }

    public Set<Product> getProductSimilar(String productId) {
        if (productId == null) return Collections.emptySet();
        Set<String> ids = existingProductRepository.getProductSimilarIds(productId);
        List<CompletableFuture<Product>> productFutures = new ArrayList<>();
        for (String id : ids) {
            CompletableFuture<Product> restCall = existingProductRepository.getProductAsync(id);
            productFutures.add(restCall);
        }
        return productFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());
    }
}
