package com.strawHat.backend.dto;

import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/** Response body representing a DSA problem returned to the client. */
@Getter
@Setter
@AllArgsConstructor
public class DSAProblemResponseDto {

    private Long id;
    private String title;
    private String topic;
    private ProblemDifficulty difficulty;
    private ProblemStatus status;
    private String platform;
    private String problemUrl;
    private String notes;
    private LocalDateTime solvedAt;
    private LocalDateTime createdAt;
}
