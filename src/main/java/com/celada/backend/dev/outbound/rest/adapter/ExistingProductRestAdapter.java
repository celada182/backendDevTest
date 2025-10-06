package com.celada.backend.dev.outbound.rest.adapter;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.outbound.rest.model.ExistingProduct;

public class ExistingProductRestAdapter {
    public static Product adapt(ExistingProduct product) {
        if (product == null) return null;
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .availability(product.getAvailability())
                .build();
    }
}
