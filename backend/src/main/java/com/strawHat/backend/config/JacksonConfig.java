package com.strawHat.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Provides a Jackson bean for JSON serialization/deserialization. */
@Configuration
public class JacksonConfig {

    /** Creates the application-wide ObjectMapper used for JSON parsing. */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
