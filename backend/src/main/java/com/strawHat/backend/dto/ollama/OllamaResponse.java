package com.strawHat.backend.dto.ollama;

import lombok.Getter;
import lombok.Setter;

/** Response received from the local Ollama `/api/generate` endpoint. */
@Getter
@Setter
public class OllamaResponse {
    private String response;
}
