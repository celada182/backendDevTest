package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.Set;

@Repository
public class ExistingProductRestRepository implements ExistingProductRepository {
    @Value("${existingProductApi.uriBase}")
    private String uriBase;

    private final RestClient restClient;

    public ExistingProductRestRepository() {
        this.restClient = RestClient.create();
    }

    @Override
    public Product getProduct(String productId) {
        return restClient.get()
                .uri(uriBase + "/product/" + productId)
                .retrieve()
                .body(Product.class);
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
}
