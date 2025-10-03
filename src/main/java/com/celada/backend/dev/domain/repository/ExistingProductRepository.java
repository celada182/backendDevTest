package com.celada.backend.dev.domain.repository;

import com.celada.backend.dev.domain.model.Product;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ExistingProductRepository {
    Set<String> getProductSimilarIds(String productId);
    CompletableFuture<Product> getProductAsync(String productId);
}
