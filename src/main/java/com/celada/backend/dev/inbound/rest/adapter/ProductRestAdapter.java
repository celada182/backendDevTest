package com.celada.backend.dev.inbound.rest.adapter;

import com.celada.backend.dev.domain.model.Product;
import com.celada.openapi.model.ProductDetail;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductRestAdapter {
    public static Product adapt(ProductDetail productDetail) {
        if (productDetail == null) return null;
        return Product.builder()
                .id(productDetail.getId())
                .name(productDetail.getName())
                .price(productDetail.getPrice())
                .availability(productDetail.getAvailability())
                .build();
    }

    public static ProductDetail adapt(Product product) {
        if (product == null) return null;
        return new ProductDetail(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailability()
        );
    }

    public static Set<ProductDetail> adapt(Set<Product> products) {
        if (products == null) return null;
        return products.stream()
                .map(ProductRestAdapter::adapt)
                .collect(Collectors.toSet());
    }
}
