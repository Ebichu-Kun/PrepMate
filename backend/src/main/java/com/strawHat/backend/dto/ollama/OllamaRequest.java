package com.strawHat.backend.dto.ollama;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OllamaRequest {

    private String model;
    private String prompt;
    private boolean stream;

}
