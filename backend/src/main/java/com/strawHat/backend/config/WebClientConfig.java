package com.strawHat.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/** Provides a bean used to call the Ollama AI service. */
@Configuration
public class WebClientConfig {

    /** Creates the default WebClient instance used for outbound HTTP calls. */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
