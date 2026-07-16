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
public class InterviewResultResponseDto {

    private String overallRating;

    private Integer technicalScore;

    private Integer communicationScore;

    private List<String> strengths;

    private List<String> weaknesses;

    private List<String> suggestions;
}