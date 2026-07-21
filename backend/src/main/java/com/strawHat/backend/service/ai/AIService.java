package com.strawHat.backend.service.ai;

/** Abstraction over the underlying AI provider used for content generation. */
public interface AIService {

    /** Sends a prompt to the AI provider and returns the generated text response. */
    String generateContent(String prompt);
}
