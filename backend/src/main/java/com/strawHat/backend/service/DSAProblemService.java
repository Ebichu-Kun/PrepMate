package com.strawHat.backend.service;

import com.strawHat.backend.dto.DSAProblemRequestDto;
import com.strawHat.backend.dto.DSAProblemResponseDto;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;

import java.util.List;

/** Business operations for managing a user's tracked DSA problems. */
public interface DSAProblemService {

    /** Creates a new DSA problem owned by the current user. */
    DSAProblemResponseDto createProblem(DSAProblemRequestDto request);

    /** Returns all DSA problems owned by the current user. */
    List<DSAProblemResponseDto> getAllProblems();

    /** Returns a single DSA problem owned by the current user. */
    DSAProblemResponseDto getProblemById(Long id);

    /** Updates an existing DSA problem owned by the current user. */
    DSAProblemResponseDto updateProblem(Long id, DSAProblemRequestDto request);

    /** Deletes a DSA problem owned by the current user. */
    void deleteProblem(Long id);

    /** Returns the current user's DSA problems filtered by status. */
    List<DSAProblemResponseDto> getProblemsByStatus(ProblemStatus status);

    /** Returns the current user's DSA problems filtered by difficulty. */
    List<DSAProblemResponseDto> getProblemsByDifficulty(ProblemDifficulty difficulty);

    /** Searches the current user's DSA problems by title keyword. */
    List<DSAProblemResponseDto> searchByTitle(String title);

    /** Searches the current user's DSA problems by topic keyword. */
    List<DSAProblemResponseDto> searchByTopic(String topic);
}
