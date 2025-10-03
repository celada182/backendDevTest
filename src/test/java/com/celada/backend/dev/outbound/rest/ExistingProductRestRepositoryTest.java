package com.celada.backend.dev.outbound.rest;

import com.celada.backend.dev.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ExistingProductRestRepository.class)
class ExistingProductRestRepositoryTest {

    private static final String BASE_URI = "http://test-api.com";
    private static final String PRODUCT_ID = "test-123";
    private static final String[] SIMILAR_IDS = {"similar-1", "similar-2"};

    @Autowired
    private ExistingProductRestRepository repository;

    @Autowired
    private RestClient.Builder restClientBuilder;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    }

    @Test
    void foo() throws ExecutionException, InterruptedException {
        String uri = BASE_URI + "/product/" + PRODUCT_ID;
        mockServer.expect(requestTo(uri)).andRespond(withSuccess("aa", MediaType.APPLICATION_JSON));

        CompletableFuture<Product> result = repository.getProductAsync(PRODUCT_ID);
        assertEquals("aa", result.get().getName());
    }

//    @Test
//    void getProductSimilarIds_shouldReturnSetOfIds() {
//        // Given
//        server.expect(requestTo(BASE_URI + "/product/" + PRODUCT_ID + "/similarids"))
//                .andRespond(withSuccess(SIMILAR_IDS.toString(), MediaType.APPLICATION_JSON));
//
//        // When
//        Set<String> result = repository.getProductSimilarIds(PRODUCT_ID);
//
//        // Then
//        verify(restClient.get()).uri(BASE_URI + "/product/" + PRODUCT_ID + "/similarids");
//        assertThat(result)
//                .hasSize(2)
//                .containsExactlyInAnyOrder(SIMILAR_IDS);
//    }

//    @Test
//    void getProductSimilarIds_shouldReturnEmptySetWhenResponseIsNull() {
//        // Given
//        when(responseSpec.body(String[].class)).thenReturn(null);
//
//        // When
//        Set<String> result = repository.getProductSimilarIds(PRODUCT_ID);
//
//        // Then
//        verify(restClient.get()).uri(BASE_URI + "/product/" + PRODUCT_ID + "/similarids");
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void getProductAsync_shouldReturnProduct() throws Exception {
//        // Given
//        Product expectedProduct = createTestProduct(PRODUCT_ID);
//        when(responseSpec.body(Product.class)).thenReturn(expectedProduct);
//
//        // When
//        CompletableFuture<Product> future = repository.getProductAsync(PRODUCT_ID);
//        Product result = future.get();
//
//        // Then
//        verify(restClient.get()).uri(BASE_URI + "/product/" + PRODUCT_ID);
//        assertThat(result)
//            .isNotNull()
//            .isEqualTo(expectedProduct);
//    }
//
//    @Test
//    void getProductAsync_shouldHandleNullResponse() throws Exception {
//        // Given
//        when(responseSpec.body(Product.class)).thenReturn(null);
//
//        // When
//        CompletableFuture<Product> future = repository.getProductAsync(PRODUCT_ID);
//        Product result = future.get();
//
//        // Then
//        verify(restClient.get()).uri(BASE_URI + "/product/" + PRODUCT_ID);
//        assertThat(result).isNull();
//    }

    private Product createTestProduct(String id) {
        return Product.builder()
                .id(id)
                .name("Test Product")
                .price(new BigDecimal("29.99"))
                .availability(true)
                .build();
    }
}
