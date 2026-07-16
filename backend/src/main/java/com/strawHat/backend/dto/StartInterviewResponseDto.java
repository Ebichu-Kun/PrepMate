package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StartInterviewResponseDto {

    private Long sessionId;

    private List<InterviewQuestionResponseDto> questions;
}
