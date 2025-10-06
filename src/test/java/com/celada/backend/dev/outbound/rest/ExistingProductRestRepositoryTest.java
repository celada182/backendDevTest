package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExistingProductRestRepositoryTest {

    private static final String BASE_URI = "http://test-api.com";
    private static final String PRODUCT_ID = "test-123";
    private static final String[] SIMILAR_IDS = {"similar-1", "similar-2"};

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExistingProductRestRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ExistingProductRestRepository(restTemplate);
        ReflectionTestUtils.setField(repository, "uriBase", "http://test-api.com");
    }

    @Test
    void getProductSimilarIds_shouldReturnSetOfIds() {
        // Given
        String url = BASE_URI + "/product/" + PRODUCT_ID + "/similarids";
        when(restTemplate.getForEntity(eq(url), eq(String[].class)))
                .thenReturn(new ResponseEntity<>(SIMILAR_IDS, HttpStatus.OK));

        // When
        Set<String> result = repository.getProductSimilarIds(PRODUCT_ID);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains("similar-1"));
        assertTrue(result.contains("similar-2"));
        verify(restTemplate).getForEntity(eq(url), eq(String[].class));
    }

    @Test
    void getProductSimilarIds_shouldReturnEmptySetWhenResponseIsNull() {
        // Given
        String url = BASE_URI + "/product/" + PRODUCT_ID + "/similarids";
        when(restTemplate.getForEntity(eq(url), eq(String[].class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        // When
        Set<String> result = repository.getProductSimilarIds(PRODUCT_ID);

        // Then
        assertTrue(result.isEmpty());
        verify(restTemplate).getForEntity(eq(url), eq(String[].class));
    }

    @Test
    void getProductAsync_shouldReturnProduct() throws ExecutionException, InterruptedException {
        // Given
        Product expectedProduct = Product.builder()
                .id(PRODUCT_ID)
                .name("Test Product")
                .price(BigDecimal.TEN)
                .availability(true)
                .build();
        String url = BASE_URI + "/product/" + PRODUCT_ID;
        when(restTemplate.getForEntity(eq(url), eq(Product.class)))
                .thenReturn(new ResponseEntity<>(expectedProduct, HttpStatus.OK));

        // When
        CompletableFuture<Product> future = repository.getProductAsync(PRODUCT_ID);
        Product result = future.get();

        // Then
        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getId());
        assertEquals("Test Product", result.getName());
        verify(restTemplate).getForEntity(eq(url), eq(Product.class));
    }

    @Test
    void getProductAsync_shouldHandleNullResponse() throws Exception {
        // Given
        String url = BASE_URI + "/product/" + PRODUCT_ID;
        when(restTemplate.getForEntity(eq(url), eq(Product.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        // When
        CompletableFuture<Product> future = repository.getProductAsync(PRODUCT_ID);
        Product result = future.get();

        // Then
        assertNull(result);
        verify(restTemplate).getForEntity(eq(url), eq(Product.class));
    }
}
