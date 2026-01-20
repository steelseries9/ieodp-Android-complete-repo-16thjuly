package com.enterprisesystemengineering.pythonapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PythonApiConfig {

    @Value("${python.api.base-url}")
    private String pythonApiBaseUrl;

    @Bean
    public WebClient pythonApiWebClient() {
        return WebClient.builder()
                .baseUrl(pythonApiBaseUrl)
                .build();
    }
}

