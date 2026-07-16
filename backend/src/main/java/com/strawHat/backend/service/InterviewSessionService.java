package com.strawHat.backend.service;

import com.strawHat.backend.dto.*;

import java.util.List;

public interface InterviewSessionService {

    InterviewSessionResponseDto createInterview(
            InterviewSessionRequestDto request);

    StartInterviewResponseDto startInterview(
            InterviewSessionRequestDto request);

    List<InterviewSessionResponseDto> getAllInterviews();

    InterviewSessionResponseDto getInterviewById(Long id);

    void deleteInterview(Long id);

    void submitAnswer(Long sessionId, AnswerRequestDto request);

    InterviewResultResponseDto finishInterview(Long sessionId);
}
