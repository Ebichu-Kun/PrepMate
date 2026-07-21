package com.strawHat.backend.controller;

import com.strawHat.backend.service.ai.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Manual test endpoint for verifying the AI provider is reachable. */
@RestController
@RequestMapping("/api/test-ai")
public class AiTestController {

    private final AIService aiService;

    public AiTestController(AIService aiService) {
        this.aiService = aiService;
    }

    /** Sends the given prompt straight to the AI provider and returns its raw response. */
    @GetMapping
    public String test(@RequestParam String prompt) {
        return aiService.generateContent(prompt);
    }
}
