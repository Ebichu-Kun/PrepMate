package com.strawHat.backend.service.impl;


import com.strawHat.backend.dto.InterviewResultResponseDto;
import com.strawHat.backend.entity.InterviewQuestion;
import com.strawHat.backend.service.InterviewEvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewEvaluationServiceImpl
        implements InterviewEvaluationService {

    @Override
    public InterviewResultResponseDto evaluate(
            List<InterviewQuestion> questions) {

        return new InterviewResultResponseDto(
                "8.5 / 10",
                88,
                82,
                List.of(
                        "Strong Java fundamentals",
                        "Good Spring Boot knowledge"
                ),
                List.of(
                        "Need deeper Hibernate knowledge"
                ),
                List.of(
                        "Practice Spring Security",
                        "Revise Microservices"
                )
        );
    }
}
