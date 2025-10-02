package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private final ProductService productService = new ProductService();
    private static final String TEST_PRODUCT_ID = "test-123";
    private static final String EXPECTED_NAME = "Product 1";
    private static final BigDecimal EXPECTED_PRICE = new BigDecimal("10.00");

    @Test
    void getProductSimilar_shouldReturnNonEmptySet() {
        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        assertThat(result).isNotNull().isNotEmpty();
    }

    @Test
    void getProductSimilar_shouldReturnProductWithCorrectId() {
        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        Product product = result.iterator().next();
        assertEquals(TEST_PRODUCT_ID, product.getId(), 
            "Product ID should match the input parameter");
    }

    @Test
    void getProductSimilar_shouldReturnProductWithExpectedValues() {
        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        Product product = result.iterator().next();
        assertAll(
            () -> assertEquals(EXPECTED_NAME, product.getName(), 
                "Product name should be " + EXPECTED_NAME),
            () -> assertEquals(0, EXPECTED_PRICE.compareTo(product.getPrice()),
                "Product price should be " + EXPECTED_PRICE),
            () -> assertNull(product.getAvailability(), 
                "Availability should be null as it's not set in the service")
        );
    }

    @Test
    void getProductSimilar_shouldReturnDifferentObjectsForDifferentCalls() {
        // When
        Set<Product> firstCall = productService.getProductSimilar(TEST_PRODUCT_ID);
        Set<Product> secondCall = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then - Verify we get different object instances
        assertNotSame(firstCall, secondCall, "Should return new Set instance on each call");
        assertNotSame(
            firstCall.iterator().next(), 
            secondCall.iterator().next(),
            "Should return new Product instance on each call"
        );
    }
}
