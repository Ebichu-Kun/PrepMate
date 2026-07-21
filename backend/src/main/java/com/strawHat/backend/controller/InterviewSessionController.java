package com.strawHat.backend.controller;

import com.strawHat.backend.dto.*;
import com.strawHat.backend.service.InterviewSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Manages the authenticated user's mock interview sessions. */
@RestController
@RequestMapping("/api/interviews")
public class InterviewSessionController {

    private final InterviewSessionService interviewSessionService;

    public InterviewSessionController(InterviewSessionService interviewSessionService) {
        this.interviewSessionService = interviewSessionService;
    }

    /** Creates a bare interview session (without generating questions). */
    @PostMapping
    public ResponseEntity<ApiResponse<InterviewSessionResponseDto>> createInterview(
            @RequestBody @Valid InterviewSessionRequestDto request) {

        InterviewSessionResponseDto response = interviewSessionService.createInterview(request);

        ApiResponse<InterviewSessionResponseDto> apiResponse =
                new ApiResponse<>(true, "Interview session created successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /** Creates a new interview session and generates its questions via AI. */
    @PostMapping("/start")
    public ResponseEntity<ApiResponse<StartInterviewResponseDto>> startInterview(
            @Valid @RequestBody InterviewSessionRequestDto request) {

        StartInterviewResponseDto response = interviewSessionService.startInterview(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Interview started successfully", response));
    }

    /** Returns all interview sessions belonging to the current user. */
    @GetMapping
    public ResponseEntity<ApiResponse<List<InterviewSessionResponseDto>>> getAllInterviews() {

        List<InterviewSessionResponseDto> response = interviewSessionService.getAllInterviews();

        ApiResponse<List<InterviewSessionResponseDto>> apiResponse =
                new ApiResponse<>(true, "Interview sessions fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    /** Returns a single interview session by id. */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewSessionResponseDto>> getInterviewById(
            @PathVariable Long id) {

        InterviewSessionResponseDto response = interviewSessionService.getInterviewById(id);

        ApiResponse<InterviewSessionResponseDto> apiResponse =
                new ApiResponse<>(true, "Interview session fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    /** Deletes an interview session by id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInterview(@PathVariable Long id) {

        interviewSessionService.deleteInterview(id);

        ApiResponse<Void> response =
                new ApiResponse<>(true, "Interview session deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    /** Submits the current user's answer to a specific question in a session. */
    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<ApiResponse<Void>> submitAnswer(
            @PathVariable Long sessionId,
            @Valid @RequestBody AnswerRequestDto request) {

        interviewSessionService.submitAnswer(sessionId, request);

        ApiResponse<Void> response =
                new ApiResponse<>(true, "Answer submitted successfully", null);

        return ResponseEntity.ok(response);
    }

    /** Finishes an interview session: triggers AI evaluation and returns the result. */
    @PostMapping("/{sessionId}/finish")
    public ResponseEntity<ApiResponse<InterviewResultResponseDto>> finishInterview(
            @PathVariable Long sessionId) {

        InterviewResultResponseDto response = interviewSessionService.finishInterview(sessionId);

        return ResponseEntity.ok(new ApiResponse<>(true, "Interview completed successfully", response));
    }
}
