package com.enterprisesystemengineering.pythonapi.client;

import com.enterprisesystemengineering.pythonapi.dto.AutomationEvent;
import com.enterprisesystemengineering.pythonapi.dto.AutomationResponse;
import com.enterprisesystemengineering.pythonapi.dto.HealthCheckResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
public class PythonApiClient {

    private final WebClient webClient;

    public PythonApiClient(WebClient pythonApiWebClient) {
        this.webClient = pythonApiWebClient;
    }

    public HealthCheckResponse checkHealth() {
        try {
            return webClient.get()
                    .uri("/")
                    .retrieve()
                    .bodyToMono(HealthCheckResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to check Python API health", e);
        }
    }

    public AutomationResponse executeAutomation(AutomationEvent event) {
        try {
            return webClient.post()
                    .uri("/api/v1/automation/execute")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Correlation-ID", UUID.randomUUID().toString())
                    .bodyValue(event)
                    .retrieve()
                    .bodyToMono(AutomationResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to execute automation", e);
        }
    }
}

