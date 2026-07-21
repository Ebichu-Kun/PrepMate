package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.*;
import com.strawHat.backend.entity.InterviewQuestion;
import com.strawHat.backend.entity.InterviewSession;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.exception.InterviewQuestionNotFoundException;
import com.strawHat.backend.exception.InterviewSessionNotFoundException;
import com.strawHat.backend.exception.UnauthorizedAccessException;
import com.strawHat.backend.exception.UserNotFoundException;
import com.strawHat.backend.repository.InterviewQuestionRepository;
import com.strawHat.backend.repository.InterviewSessionRepository;
import com.strawHat.backend.repository.UserRepository;
import com.strawHat.backend.service.InterviewEvaluationService;
import com.strawHat.backend.service.InterviewSessionService;
import com.strawHat.backend.service.QuestionGeneratorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the full lifecycle of a mock interview session: creation,
 * AI-generated questions, answer submission, and final evaluation.
 */
@Service
public class InterviewSessionServiceImplementation implements InterviewSessionService {

    private final InterviewSessionRepository interviewSessionRepository;
    private final UserRepository userRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final QuestionGeneratorService questionGeneratorService;
    private final InterviewEvaluationService interviewEvaluationService;

    public InterviewSessionServiceImplementation(
            InterviewSessionRepository interviewSessionRepository,
            UserRepository userRepository,
            InterviewQuestionRepository interviewQuestionRepository,
            QuestionGeneratorService questionGeneratorService,
            InterviewEvaluationService interviewEvaluationService) {

        this.interviewSessionRepository = interviewSessionRepository;
        this.userRepository = userRepository;
        this.interviewQuestionRepository = interviewQuestionRepository;
        this.questionGeneratorService = questionGeneratorService;
        this.interviewEvaluationService = interviewEvaluationService;
    }

    /** Resolves the currently authenticated user from the security context. */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /** Creates a bare interview session (no questions generated yet). */
    @Override
    public InterviewSessionResponseDto createInterview(InterviewSessionRequestDto request) {

        User currentUser = getCurrentUser();

        InterviewSession session = new InterviewSession();

        session.setRole(request.getRole());
        session.setDifficulty(request.getDifficulty());
        session.setUser(currentUser);

        InterviewSession savedSession = interviewSessionRepository.save(session);

        return mapToResponseDto(savedSession);
    }

    /** Returns all interview sessions belonging to the current user. */
    @Override
    public List<InterviewSessionResponseDto> getAllInterviews() {

        User currentUser = getCurrentUser();

        List<InterviewSession> sessions = interviewSessionRepository.findByUser(currentUser);

        return sessions.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Returns a single interview session, provided it belongs to the current user. */
    @Override
    public InterviewSessionResponseDto getInterviewById(Long id) {

        User currentUser = getCurrentUser();

        InterviewSession session = findSessionByIdAndUser(id, currentUser);

        return mapToResponseDto(session);
    }

    /** Deletes an interview session, provided it belongs to the current user. */
    @Override
    public void deleteInterview(Long id) {

        User currentUser = getCurrentUser();

        InterviewSession session = findSessionByIdAndUser(id, currentUser);

        interviewSessionRepository.delete(session);
    }

    /**
     * Creates a new interview session and generates its questions via AI,
     * saving both before returning them to the client.
     */
    @Override
    public StartInterviewResponseDto startInterview(InterviewSessionRequestDto request) {

        User currentUser = getCurrentUser();

        InterviewSession session = new InterviewSession();
        session.setRole(request.getRole());
        session.setDifficulty(request.getDifficulty());
        session.setUser(currentUser);

        InterviewSession savedSession = interviewSessionRepository.save(session);

        List<InterviewQuestionResponseDto> generatedQuestions =
                questionGeneratorService.generateQuestions(
                        request.getRole(),
                        request.getDifficulty(),
                        request.getNumberOfQuestions()
                );

        List<InterviewQuestion> questions = new ArrayList<>();

        for (InterviewQuestionResponseDto dto : generatedQuestions) {

            InterviewQuestion question = new InterviewQuestion();

            question.setQuestion(dto.getQuestion());
            question.setQuestionOrder(dto.getQuestionOrder());
            question.setInterviewSession(savedSession);

            questions.add(question);
        }

        interviewQuestionRepository.saveAll(questions);

        List<InterviewQuestionResponseDto> questionDtos =
                questions.stream()
                        .map(question -> new InterviewQuestionResponseDto(
                                question.getId(),
                                question.getQuestion(),
                                question.getQuestionOrder()
                        ))
                        .toList();

        return new StartInterviewResponseDto(savedSession.getId(), questionDtos);
    }

    /**
     * Records the current user's answer to a specific question, verifying
     * the session belongs to the user and the question belongs to the session.
     */
    @Override
    public void submitAnswer(Long sessionId, AnswerRequestDto request) {

        User currentUser = getCurrentUser();

        InterviewSession session =
                interviewSessionRepository
                        .findById(sessionId)
                        .orElseThrow(() ->
                                new InterviewSessionNotFoundException("Interview session not found"));

        if (!session.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to access this interview");
        }

        InterviewQuestion question =
                interviewQuestionRepository
                        .findById(request.getQuestionId())
                        .orElseThrow(() ->
                                new InterviewQuestionNotFoundException("Question not found"));

        if (!question.getInterviewSession().getId().equals(sessionId)) {
            throw new UnauthorizedAccessException("Question does not belong to this interview");
        }

        question.setAnswer(request.getAnswer());

        interviewQuestionRepository.save(question);
    }

    /**
     * Finishes an interview: sends all answered questions to the AI evaluator,
     * stores the resulting score/feedback on the session, and returns the result.
     */
    @Override
    public InterviewResultResponseDto finishInterview(Long sessionId) {

        User currentUser = getCurrentUser();

        InterviewSession session =
                interviewSessionRepository
                        .findById(sessionId)
                        .orElseThrow(() ->
                                new InterviewSessionNotFoundException("Interview not found"));

        if (!session.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to access this interview");
        }

        List<InterviewQuestion> questions =
                interviewQuestionRepository
                        .findByInterviewSessionIdOrderByQuestionOrderAsc(sessionId);

        InterviewResultResponseDto response = interviewEvaluationService.evaluate(questions);

        session.setScore(response.getTechnicalScore());
        session.setFeedback(response.getOverallRating());

        interviewSessionRepository.save(session);

        return response;
    }

    /** Finds a session by id, throwing if it doesn't exist or isn't owned by the user. */
    private InterviewSession findSessionByIdAndUser(Long id, User user) {
        return interviewSessionRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new InterviewSessionNotFoundException("Interview session not found"));
    }

    /** Maps an InterviewSession entity to its response DTO (questions omitted here). */
    private InterviewSessionResponseDto mapToResponseDto(InterviewSession session) {
        return new InterviewSessionResponseDto(
                session.getId(),
                session.getRole(),
                session.getDifficulty(),
                session.getScore(),
                session.getFeedback(),
                session.getCreatedAt(),
                List.of()
        );
    }
}
