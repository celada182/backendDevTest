package com.celada.backend.dev.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
        rf.setConnectionRequestTimeout(Duration.ofSeconds(5));
        rf.setConnectTimeout(Duration.ofSeconds(5));
        rf.setReadTimeout(Duration.ofSeconds(5));
        restTemplate.setRequestFactory(rf);
        return restTemplate;
    }
}
