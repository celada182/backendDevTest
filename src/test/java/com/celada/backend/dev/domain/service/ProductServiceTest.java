package com.celada.backend.dev.domain.service;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.domain.repository.ExistingProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ExistingProductRepository existingProductRepository;

    @InjectMocks
    private ProductService productService;

    private static final String TEST_PRODUCT_ID = "test-123";
    private static final String SIMILAR_PRODUCT_ID = "similar-456";
    private static final String TEST_PRODUCT_NAME = "Test Product";
    private static final BigDecimal TEST_PRICE = new BigDecimal("29.99");
    private static final boolean TEST_AVAILABILITY = true;

    @Test
    void getProductSimilar_shouldReturnEmptySetForNullInput() {
        // When
        Set<Product> result = productService.getProductSimilar(null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getProductSimilar_shouldCallRepositoryWithCorrectId() {
        // Given
        Product testProduct = createTestProduct(TEST_PRODUCT_ID);
        Product similarProduct = createSimilarProduct(SIMILAR_PRODUCT_ID);
        when(existingProductRepository.getSimilarProducts(TEST_PRODUCT_ID))
                .thenReturn(Set.of(testProduct, similarProduct));

        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        verify(existingProductRepository).getSimilarProducts(TEST_PRODUCT_ID);
        assertThat(result).hasSize(2)
                .extracting(Product::getId)
                .containsExactlyInAnyOrder(TEST_PRODUCT_ID, SIMILAR_PRODUCT_ID);
    }

    @Test
    void getProductSimilar_shouldReturnEmptySetWhenRepositoryReturnsEmpty() {
        // Given
        when(existingProductRepository.getSimilarProducts(TEST_PRODUCT_ID))
                .thenReturn(Collections.emptySet());

        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        assertThat(result).isEmpty();
    }

    private Product createTestProduct(String id) {
        return Product.builder()
                .id(id)
                .name(TEST_PRODUCT_NAME)
                .price(TEST_PRICE)
                .availability(TEST_AVAILABILITY)
                .build();
    }

    private Product createSimilarProduct(String id) {
        return Product.builder()
                .id(id)
                .name("Similar Product")
                .price(new BigDecimal("19.99"))
                .availability(true)
                .build();
    }
}