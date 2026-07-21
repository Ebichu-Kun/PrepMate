package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/** Response body representing an interview session returned to the client. */
@Getter
@Setter
@AllArgsConstructor
public class InterviewSessionResponseDto {

    private Long id;
    private String role;
    private String difficulty;
    private Integer score;
    private String feedback;
    private LocalDateTime createdAt;
    private List<InterviewQuestionResponseDto> questions;
}
