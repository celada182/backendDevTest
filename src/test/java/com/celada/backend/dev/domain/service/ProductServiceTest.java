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
import java.util.concurrent.CompletableFuture;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        Set<String> similarIds = new HashSet<>();
        similarIds.add(SIMILAR_PRODUCT_ID);
        similarIds.add("another-789");
        
        Product similarProduct1 = createSimilarProduct(SIMILAR_PRODUCT_ID);
        Product similarProduct2 = createSimilarProduct("another-789");
        
        when(existingProductRepository.getProductSimilarIds(TEST_PRODUCT_ID)).thenReturn(similarIds);
        when(existingProductRepository.getProductAsync(SIMILAR_PRODUCT_ID))
                .thenReturn(CompletableFuture.completedFuture(similarProduct1));
        when(existingProductRepository.getProductAsync("another-789"))
                .thenReturn(CompletableFuture.completedFuture(similarProduct2));

        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        verify(existingProductRepository).getProductSimilarIds(TEST_PRODUCT_ID);
        verify(existingProductRepository).getProductAsync(SIMILAR_PRODUCT_ID);
        verify(existingProductRepository).getProductAsync("another-789");
        
        assertThat(result).hasSize(2)
                .extracting(Product::getId)
                .containsExactlyInAnyOrder(SIMILAR_PRODUCT_ID, "another-789");
    }

    @Test
    void getProductSimilar_shouldReturnEmptySetWhenNoSimilarIdsFound() {
        // Given
        when(existingProductRepository.getProductSimilarIds(TEST_PRODUCT_ID))
                .thenReturn(Collections.emptySet());

        // When
        Set<Product> result = productService.getProductSimilar(TEST_PRODUCT_ID);

        // Then
        verify(existingProductRepository).getProductSimilarIds(TEST_PRODUCT_ID);
        verify(existingProductRepository, never()).getProductAsync(anyString());
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