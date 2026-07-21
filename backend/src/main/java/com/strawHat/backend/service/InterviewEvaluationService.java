package com.strawHat.backend.service;

import com.strawHat.backend.dto.InterviewResultResponseDto;
import com.strawHat.backend.entity.InterviewQuestion;

import java.util.List;

/** Evaluates a completed interview's questions/answers using AI and produces a scored result. */
public interface InterviewEvaluationService {

    /** Evaluates the given answered questions and returns an overall scored result. */
    InterviewResultResponseDto evaluate(List<InterviewQuestion> questions);
}
