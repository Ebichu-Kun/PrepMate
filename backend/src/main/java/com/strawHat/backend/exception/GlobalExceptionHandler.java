package com.strawHat.backend.exception;

import com.strawHat.backend.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handling for all controllers. Converts known
 * exceptions into a consistent {@link ApiResponse} error envelope with an
 * appropriate HTTP status code.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Handles @Valid request body validation failures, returning a field -> message map. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(false, "Validation Failed", errors);

        return ResponseEntity.badRequest().body(response);
    }

    /** Handles registration attempts with an email that is already taken. */
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExistException(
            EmailAlreadyExistException ex) {

        ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /** Handles failed login attempts due to incorrect email or password. */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /** Handles cases where the authenticated/requested user cannot be found. */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(
            UserNotFoundException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Handles requests for a note that does not exist or isn't owned by the caller. */
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoteNotFoundException(
            NoteNotFoundException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Handles attempts to access or modify a resource the caller does not own. */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedAccessException(
            UnauthorizedAccessException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /** Handles requests for an interview session that does not exist or isn't owned by the caller. */
    @ExceptionHandler(InterviewSessionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleInterviewSessionNotFoundException(
            InterviewSessionNotFoundException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Handles requests for an interview question that does not exist. */
    @ExceptionHandler(InterviewQuestionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleInterviewQuestionNotFoundException(
            InterviewQuestionNotFoundException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Handles requests for a DSA problem that does not exist or isn't owned by the caller. */
    @ExceptionHandler(DSAProblemNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDSAProblemNotFoundException(
            DSAProblemNotFoundException ex) {

        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
