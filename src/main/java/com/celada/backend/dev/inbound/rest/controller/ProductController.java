package com.celada.backend.dev.inbound.rest.controller;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.service.ProductService;
import com.celada.backend.dev.inbound.rest.adapter.ProductRestAdapter;
import com.celada.openapi.api.ProductApi;
import com.celada.openapi.model.ProductDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        if (productId == null || productId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Set<Product> products = productService.getProductSimilar(productId);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Set<ProductDetail> productDetails = ProductRestAdapter.adapt(products);
        return ResponseEntity.ok(productDetails);
    }
}
