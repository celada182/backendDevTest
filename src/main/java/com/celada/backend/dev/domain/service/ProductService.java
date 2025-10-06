package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductService {

    private final ExistingProductRepository existingProductRepository;

    public ProductService(ExistingProductRepository existingProductRepository) {
        this.existingProductRepository = existingProductRepository;
    }

    public Set<Product> getProductSimilar(String productId) {
        if (productId == null) return Collections.emptySet();
        Set<String> ids = existingProductRepository.getProductSimilarIds(productId);
        List<CompletableFuture<Product>> productFutures = new ArrayList<>();
        log.info("Getting products {}", ids);
        for (String id : ids) {
            CompletableFuture<Product> restCall = existingProductRepository.getProductAsync(id);
            productFutures.add(restCall);
        }
        return productFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }
}
