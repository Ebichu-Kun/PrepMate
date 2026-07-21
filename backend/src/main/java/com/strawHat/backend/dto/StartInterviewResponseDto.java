package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/** Response body returned when an interview is started, containing the generated questions. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StartInterviewResponseDto {
    private Long sessionId;
    private List<InterviewQuestionResponseDto> questions;
}
