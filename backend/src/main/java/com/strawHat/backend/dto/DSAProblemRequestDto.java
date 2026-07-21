package com.strawHat.backend.dto;

import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** Request body for creating or updating a DSA problem. */
@Getter
@Setter
public class DSAProblemRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Difficulty is required")
    private ProblemDifficulty difficulty;

    private ProblemStatus status;

    private String platform;

    private String problemUrl;

    private String notes;
}
