package com.celada.backend.dev.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "existing-product-api")
@Data
public class ExistingProductApiConfiguration {
    private String base;
    private String product;
    private String similarIds;
}
