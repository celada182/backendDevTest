package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.configuration.ExistingProductApiConfiguration;
import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import com.celada.backend.dev.outbound.rest.adapter.ExistingProductRestAdapter;
import com.celada.backend.dev.outbound.rest.model.ExistingProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.emptySet;

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
        try {
            String[] result = restTemplate.getForEntity(url, String[].class).getBody();
            if (result == null) return emptySet();
            return Set.of(result);
        } catch (RestClientException e) {
            log.error("Error getting similar ids for product {}", productId, e);
            return emptySet();
        }
    }

    @Override
    @Async
    public CompletableFuture<Product> getProductAsync(String productId) {
        log.info("Getting product {}", productId);
        String url = config.getBase() + String.format(config.getProduct(), productId);
        try {
            ExistingProduct existingProduct = restTemplate.getForEntity(url, ExistingProduct.class).getBody();
            Product product = ExistingProductRestAdapter.adapt(existingProduct);
            return CompletableFuture.completedFuture(product);
        } catch (RestClientException e) {
            log.error("Error getting product {}", productId, e);
            return CompletableFuture.completedFuture(null);
        }

    }
}
