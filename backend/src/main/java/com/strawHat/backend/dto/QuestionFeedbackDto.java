package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** AI feedback for a single interview question, keyed by its order in the session. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFeedbackDto {
    private Integer questionOrder;
    private Integer score;
    private String feedback;
}
