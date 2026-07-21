package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Response body representing a single interview question sent to the client. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewQuestionResponseDto {

    private Long id;
    private String question;
    private Integer questionOrder;
}
