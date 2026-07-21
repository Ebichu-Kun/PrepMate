package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.InterviewQuestionResponseDto;
import com.strawHat.backend.service.QuestionGeneratorService;
import com.strawHat.backend.service.ai.AIService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates interview questions by prompting the AI provider and parsing
 * its plain-text, one-question-per-line response.
 */
@Service
public class QuestionGeneratorServiceImpl implements QuestionGeneratorService {

    private final AIService aiService;

    public QuestionGeneratorServiceImpl(AIService aiService) {
        this.aiService = aiService;
    }

    /**
     * Builds a prompt asking for the requested number of questions, sends it
     * to the AI provider, and parses the response into ordered question DTOs.
     */
    @Override
    public List<InterviewQuestionResponseDto> generateQuestions(
            String role,
            String difficulty,
            Integer numberOfQuestions) {

        String prompt = """
            Generate exactly %d interview questions.

            Role: %s
            Difficulty: %s

            Rules:
            - One question per line
            - No numbering
            - No bullet points
            - No explanations
            """.formatted(numberOfQuestions, role, difficulty);

        String response = aiService.generateContent(prompt);

        String[] lines = response.split("\\r?\\n");

        List<InterviewQuestionResponseDto> questions = new ArrayList<>();

        long id = 1;

        for (String line : lines) {

            line = line.trim()
                    .replaceFirst("^\\d+[.)]\\s*", "")
                    .replaceFirst("^-\\s*", "");

            if (!line.isBlank()) {
                questions.add(new InterviewQuestionResponseDto(id, line, (int) id));
                id++;
            }
        }

        return questions;
    }
}
