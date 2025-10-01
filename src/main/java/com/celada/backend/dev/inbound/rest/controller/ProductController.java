package com.celada.backend.dev.inbound.rest.controller;

import com.celada.openapi.api.ProductApi;
import com.celada.openapi.model.ProductDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ProductController implements ProductApi {
    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        Set<ProductDetail> productDetails = new HashSet<>();
        productDetails.add(new ProductDetail().id("1").name("Product 1").price(BigDecimal.TEN));
        return ResponseEntity.ok(productDetails);
    }
}
