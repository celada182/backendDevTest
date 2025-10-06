package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.configuration.ExistingProductApiConfiguration;
import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Repository
@Slf4j
public class ExistingProductRestRepository implements ExistingProductRepository {

    private final ExistingProductApiConfiguration config;

    private final RestTemplate restTemplate;

    public ExistingProductRestRepository(RestTemplate restTemplate, ExistingProductApiConfiguration config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    @Override
    public Set<String> getProductSimilarIds(String productId) {
        String url = config.getBase() + String.format(config.getSimilarIds(), productId);
        String[] result = restTemplate.getForEntity(url, String[].class).getBody();
        if (result == null) return Collections.emptySet();
        return Set.of(result);
    }

    @Override
    @Async
    public CompletableFuture<Product> getProductAsync(String productId) {
        log.info("Getting product {}", productId);
        String url = config.getBase() + String.format(config.getProduct(), productId);
        Product product = restTemplate.getForEntity(url, Product.class).getBody();
        return CompletableFuture.completedFuture(product);
    }
}
