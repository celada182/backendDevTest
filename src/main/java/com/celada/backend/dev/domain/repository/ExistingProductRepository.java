package com.celada.backend.dev.domain.repository;

import com.celada.backend.dev.domain.model.Product;

import java.util.Set;

public interface ExistingProductRepository {
    public Product getProduct(String productId);
    public Set<String> getProductSimilarIds(String productId);
    public Set<Product> getSimilarProducts(String productId);
}
