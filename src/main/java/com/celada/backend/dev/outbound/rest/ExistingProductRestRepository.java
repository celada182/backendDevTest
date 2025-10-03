package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Repository
@Slf4j
public class ExistingProductRestRepository implements ExistingProductRepository {
    @Value("${existingProductApi.uriBase}")
    private String uriBase;

    private final RestClient restClient;

    public ExistingProductRestRepository() {
        this.restClient = RestClient.create();
    }

    @Override
    public Set<String> getProductSimilarIds(String productId) {
        String[] result = restClient.get()
                .uri(uriBase + "/product/" + productId + "/similarids")
                .retrieve()
                .body(String[].class);
        if (result == null) return Collections.emptySet();
        return Set.of(result);
    }

    @Override
    @Async
    public CompletableFuture<Product> getProductAsync(String productId) {
        log.info("Getting product {}", productId);
        Product product = restClient.get()
                .uri(uriBase + "/product/" + productId)
                .retrieve()
                .body(Product.class);
        return CompletableFuture.completedFuture(product);
    }
}
