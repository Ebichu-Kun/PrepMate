package com.strawHat.backend.controller;



import com.strawHat.backend.service.ai.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-ai")
public class AiTestController {

    private final AIService aiService;

    public AiTestController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public String test(@RequestParam String prompt) {
        return aiService.generateContent(prompt);
    }
}
