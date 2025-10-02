package com.celada.backend.dev.inbound.rest.adapter;

import com.celada.backend.dev.domain.model.Product;
import com.celada.openapi.model.ProductDetail;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductRestAdapterTest {

    private static final String TEST_ID = "test-id-123";
    private static final String TEST_NAME = "Test Product";
    private static final BigDecimal TEST_PRICE = new BigDecimal("29.99");
    private static final boolean TEST_AVAILABILITY = true;

    @Test
    void adapt_ProductToProductDetail_shouldMapAllFields() {
        // Given
        Product product = Product.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .price(TEST_PRICE)
                .availability(TEST_AVAILABILITY)
                .build();

        // When
        ProductDetail result = ProductRestAdapter.adapt(product);

        // Then
        assertThat(result)
                .isNotNull()
                .satisfies(detail -> {
                    assertEquals(TEST_ID, detail.getId());
                    assertEquals(TEST_NAME, detail.getName());
                    assertEquals(0, TEST_PRICE.compareTo(detail.getPrice()));
                    assertEquals(TEST_AVAILABILITY, detail.getAvailability());
                });
    }

    @Test
    void adapt_ProductDetailToProduct_shouldMapAllFields() {
        // Given
        ProductDetail detail = new ProductDetail()
                .id(TEST_ID)
                .name(TEST_NAME)
                .price(TEST_PRICE)
                .availability(TEST_AVAILABILITY);

        // When
        Product result = ProductRestAdapter.adapt(detail);

        // Then
        assertThat(result)
                .isNotNull()
                .satisfies(product -> {
                    assertEquals(TEST_ID, product.getId());
                    assertEquals(TEST_NAME, product.getName());
                    assertEquals(0, TEST_PRICE.compareTo(product.getPrice()));
                    assertEquals(TEST_AVAILABILITY, product.getAvailability());
                });
    }

    @Test
    void adapt_ProductSet_shouldConvertAllItems() {
        // Given
        Product product1 = createTestProduct("1");
        Product product2 = createTestProduct("2");
        Set<Product> products = Set.of(product1, product2);

        // When
        Set<ProductDetail> result = ProductRestAdapter.adapt(products);

        // Then
        assertThat(result)
                .hasSize(2)
                .extracting(ProductDetail::getId)
                .containsExactlyInAnyOrder("1", "2");
    }

    @Test
    void adapt_NullInput_shouldHandleGracefully() {
        // When
        ProductDetail nullProductDetail = ProductRestAdapter.adapt((Product) null);
        Product nullProduct = ProductRestAdapter.adapt((ProductDetail) null);
        Set<ProductDetail> nullSet = ProductRestAdapter.adapt((Set<Product>) null);

        // Then
        assertAll(
                () -> assertNull(nullProductDetail, "Should return null for null Product input"),
                () -> assertNull(nullProduct, "Should return null for null ProductDetail input"),
                () -> assertNull(nullSet, "Should return null for null Set input")
        );
    }

    @Test
    void adapt_EmptySet_shouldReturnEmptySet() {
        // When
        Set<ProductDetail> result = ProductRestAdapter.adapt(Set.of());

        // Then
        assertThat(result).isNotNull().isEmpty();
    }

    private Product createTestProduct(String id) {
        return Product.builder()
                .id(id)
                .name(TEST_NAME + " " + id)
                .price(TEST_PRICE)
                .availability(TEST_AVAILABILITY)
                .build();
    }
}
