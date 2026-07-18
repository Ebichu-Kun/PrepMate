package com.strawHat.backend.service;

import com.strawHat.backend.dto.DSAProblemRequestDto;
import com.strawHat.backend.dto.DSAProblemResponseDto;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;

import java.util.List;

public interface DSAProblemService {

    DSAProblemResponseDto createProblem(DSAProblemRequestDto request);

    List<DSAProblemResponseDto> getAllProblems();

    DSAProblemResponseDto getProblemById(Long id);

    DSAProblemResponseDto updateProblem(
            Long id,
            DSAProblemRequestDto request
    );

    void deleteProblem(Long id);

    List<DSAProblemResponseDto> getProblemsByStatus(
            ProblemStatus status
    );

    List<DSAProblemResponseDto> getProblemsByDifficulty(
            ProblemDifficulty difficulty
    );

    List<DSAProblemResponseDto> searchByTitle(
            String title
    );

    List<DSAProblemResponseDto> searchByTopic(
            String topic
    );
}