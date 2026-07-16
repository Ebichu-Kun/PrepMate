package com.strawHat.backend.service;

import com.strawHat.backend.dto.InterviewResultResponseDto;
import com.strawHat.backend.entity.InterviewQuestion;

import java.util.List;

public interface InterviewEvaluationService {

    InterviewResultResponseDto evaluate(
            List<InterviewQuestion> questions
    );

}
