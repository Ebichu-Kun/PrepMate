package com.strawHat.backend.service.impl;

import com.strawHat.backend.service.QuestionGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionGeneratorServiceImpl
        implements QuestionGeneratorService {

    @Override
    public List<String> generateQuestions(
            String role,
            String difficulty,
            Integer numberOfQuestions) {

        return List.of(
                "What is your name.",
                "Difference between @Component and @Service.",
                "What is JWT?",
                "Explain Spring Security.",
                "What is Hibernate?"
        );
    }
}