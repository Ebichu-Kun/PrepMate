package com.strawHat.backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewSessionRequestDto {

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Difficulty is required")
    private String difficulty;

    @NotNull(message = "Number of questions is required")
    private Integer numberOfQuestions;
}
