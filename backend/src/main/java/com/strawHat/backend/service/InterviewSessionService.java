package com.strawHat.backend.service;

import com.strawHat.backend.dto.*;

import java.util.List;

/** Business operations for managing a user's mock interview sessions. */
public interface InterviewSessionService {

    /** Creates a bare interview session (without generating questions). */
    InterviewSessionResponseDto createInterview(InterviewSessionRequestDto request);

    /** Creates an interview session and generates its questions via AI. */
    StartInterviewResponseDto startInterview(InterviewSessionRequestDto request);

    /** Returns all interview sessions owned by the current user. */
    List<InterviewSessionResponseDto> getAllInterviews();

    /** Returns a single interview session owned by the current user. */
    InterviewSessionResponseDto getInterviewById(Long id);

    /** Deletes an interview session owned by the current user. */
    void deleteInterview(Long id);

    /** Records the current user's answer to a specific question in a session. */
    void submitAnswer(Long sessionId, AnswerRequestDto request);

    /** Finishes an interview session: evaluates all answers and stores the score/feedback. */
    InterviewResultResponseDto finishInterview(Long sessionId);
}
