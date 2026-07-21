package com.strawHat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** Request body for submitting an answer to an interview question. */
@Getter
@Setter
public class AnswerRequestDto {

    @NotNull(message = "Question id is required")
    private Long questionId;

    @NotBlank(message = "Answer cannot be blank")
    private String answer;
}
