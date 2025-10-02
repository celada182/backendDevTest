package com.celada.backend.dev.inbound.rest.controller;

import com.celada.openapi.model.ProductDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductSimilar_shouldReturnSuccessResponse() {
        // Given
        String productId = "1";

        // When
        ResponseEntity<Set<ProductDetail>> response = productController.getProductSimilar(productId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getProductSimilar_shouldReturnCorrectProductDetails() {
        // Given
        String productId = "1";

        // When
        ResponseEntity<Set<ProductDetail>> response = productController.getProductSimilar(productId);

        // Then
        Set<ProductDetail> productDetails = response.getBody();
        assertNotNull(productDetails);
        assertFalse(productDetails.isEmpty());

        // Verify the first product's details
        ProductDetail product = productDetails.iterator().next();
        assertEquals("1", product.getId());
        assertEquals("Product 1", product.getName());
        assertEquals(BigDecimal.TEN, product.getPrice());
    }

    @Test
    void getProductSimilar_shouldNotDependOnInput() {
        // Given
        String anyProductId = "any-id";

        // When
        ResponseEntity<Set<ProductDetail>> response = productController.getProductSimilar(anyProductId);

        // Then - should work with any input since the current implementation doesn't use the input
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}
