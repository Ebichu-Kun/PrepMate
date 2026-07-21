package com.strawHat.backend.dto.ollama;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Request sent to the local Ollama `/api/generate` endpoint. */
@Getter
@Setter
@AllArgsConstructor
public class OllamaRequest {
    private String model;
    private String prompt;
    private boolean stream;
}
