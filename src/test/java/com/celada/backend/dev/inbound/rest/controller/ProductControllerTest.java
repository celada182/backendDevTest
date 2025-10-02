package com.celada.backend.dev.inbound.rest.controller;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.service.ProductService;
import com.celada.backend.dev.inbound.rest.adapter.ProductRestAdapter;
import com.celada.openapi.model.ProductDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private static final String TEST_PRODUCT_ID = "1";
    private static final String TEST_PRODUCT_NAME = "Test Product";
    private static final BigDecimal TEST_PRICE = new BigDecimal("19.99");
    private static final Boolean TEST_AVAILABILITY = true;

    @Test
    void getProductSimilar_shouldReturnSuccessResponse() {
        // Given
        Product testProduct = createTestProduct();
        when(productService.getProductSimilar(anyString()))
            .thenReturn(Set.of(testProduct));

        // When
        ResponseEntity<Set<ProductDetail>> response = productController.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
    }

    @Test
    void getProductSimilar_shouldReturnCorrectProductDetails() {
        // Given
        Product testProduct = createTestProduct();
        when(productService.getProductSimilar(TEST_PRODUCT_ID))
            .thenReturn(Set.of(testProduct));

        // When
        ResponseEntity<Set<ProductDetail>> response = productController.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        Set<ProductDetail> productDetails = response.getBody();
        assertNotNull(productDetails);
        assertEquals(1, productDetails.size());
        
        ProductDetail productDetail = productDetails.iterator().next();
        assertEquals(TEST_PRODUCT_ID, productDetail.getId());
        assertEquals(TEST_PRODUCT_NAME, productDetail.getName());
        assertEquals(TEST_PRICE, productDetail.getPrice());
        assertEquals(TEST_AVAILABILITY, productDetail.getAvailability());
    }

    @Test
    void getProductSimilar_shouldCallServiceWithCorrectId() {
        // Given
        String expectedProductId = "test-id-123";
        when(productService.getProductSimilar(expectedProductId))
            .thenReturn(Set.of(createTestProduct()));

        // When
        productController.getProductSimilar(expectedProductId);

        // Then - verify is handled by Mockito's strict stubbing
    }

    private Product createTestProduct() {
        return Product.builder()
            .id(TEST_PRODUCT_ID)
            .name(TEST_PRODUCT_NAME)
            .price(TEST_PRICE)
            .availability(TEST_AVAILABILITY)
            .build();
    }
}
