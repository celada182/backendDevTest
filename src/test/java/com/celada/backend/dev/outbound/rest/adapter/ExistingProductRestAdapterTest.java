package com.celada.backend.dev.outbound.rest.adapter;

import com.celada.backend.dev.domain.model.Product;
import com.celada.backend.dev.outbound.rest.model.ExistingProduct;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExistingProductRestAdapterTest {

    private static final String TEST_ID = "test-id-123";
    private static final String TEST_NAME = "Test Product";
    private static final BigDecimal TEST_PRICE = new BigDecimal("29.99");
    private static final boolean TEST_AVAILABILITY = true;

    @Test
    void adapt_ExistingProductToProduct_shouldMapAllFields() {
        // Given
        ExistingProduct detail = ExistingProduct.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .price(TEST_PRICE)
                .availability(TEST_AVAILABILITY)
                .build();

        // When
        Product result = ExistingProductRestAdapter.adapt(detail);

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
}
