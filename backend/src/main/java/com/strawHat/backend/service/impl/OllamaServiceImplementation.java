package com.strawHat.backend.service.impl;


import com.strawHat.backend.dto.ollama.OllamaRequest;
import com.strawHat.backend.dto.ollama.OllamaResponse;
import com.strawHat.backend.service.ai.AIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaServiceImplementation implements AIService {

    private final WebClient webClient;

    @Value("${ollama.url}")
    private String url;

    @Value("${ollama.model}")
    private String model;

    public OllamaServiceImplementation(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String generateContent(String prompt) {

        OllamaRequest request =
                new OllamaRequest(model, prompt, false);

        OllamaResponse response = webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OllamaResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            throw new RuntimeException("No response received from Ollama.");
        }

        return response.getResponse();
    }
}
