package com.strawHat.backend.service;

import com.strawHat.backend.dto.InterviewQuestionResponseDto;

import java.util.List;

/** Generates interview questions for a given role/difficulty using AI. */
public interface QuestionGeneratorService {

    /** Generates the requested number of interview questions for a role and difficulty. */
    List<InterviewQuestionResponseDto> generateQuestions(
            String role,
            String difficulty,
            Integer numberOfQuestions
    );
}
