package com.strawHat.backend.service;

import com.strawHat.backend.dto.InterviewQuestionResponseDto;

import java.util.List;

public interface QuestionGeneratorService {

    List<InterviewQuestionResponseDto> generateQuestions(
            String role,
            String difficulty,
            Integer numberOfQuestions
    );

}