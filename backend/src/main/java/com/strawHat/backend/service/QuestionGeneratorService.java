package com.strawHat.backend.service;

import java.util.List;

public interface QuestionGeneratorService {

    List<String> generateQuestions(
            String role,
            String difficulty,
            Integer numberOfQuestions
    );

}