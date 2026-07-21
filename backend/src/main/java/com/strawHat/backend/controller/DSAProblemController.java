package com.strawHat.backend.controller;

import com.strawHat.backend.dto.ApiResponse;
import com.strawHat.backend.dto.DSAProblemRequestDto;
import com.strawHat.backend.dto.DSAProblemResponseDto;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import com.strawHat.backend.service.DSAProblemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Manages the authenticated user's tracked DSA problems. */
@RestController
@RequestMapping("/api/dsa")
public class DSAProblemController {

    private final DSAProblemService dsaProblemService;

    public DSAProblemController(DSAProblemService dsaProblemService) {
        this.dsaProblemService = dsaProblemService;
    }

    /** Creates a new DSA problem for the current user. */
    @PostMapping
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> createProblem(
            @Valid @RequestBody DSAProblemRequestDto request) {

        DSAProblemResponseDto response = dsaProblemService.createProblem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "DSA problem created successfully", response));
    }

    /** Returns all DSA problems belonging to the current user. */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getAllProblems() {

        List<DSAProblemResponseDto> response = dsaProblemService.getAllProblems();

        return ResponseEntity.ok(new ApiResponse<>(true, "Problems fetched successfully", response));
    }

    /** Returns a single DSA problem by id. */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> getProblemById(
            @PathVariable Long id) {

        DSAProblemResponseDto response = dsaProblemService.getProblemById(id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Problem fetched successfully", response));
    }

    /** Updates an existing DSA problem by id. */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> updateProblem(
            @PathVariable Long id,
            @Valid @RequestBody DSAProblemRequestDto request) {

        DSAProblemResponseDto response = dsaProblemService.updateProblem(id, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Problem updated successfully", response));
    }

    /** Deletes a DSA problem by id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProblem(@PathVariable Long id) {

        dsaProblemService.deleteProblem(id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Problem deleted successfully", "Deleted"));
    }

    /** Returns the current user's DSA problems filtered by status. */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getProblemsByStatus(
            @PathVariable ProblemStatus status) {

        List<DSAProblemResponseDto> response = dsaProblemService.getProblemsByStatus(status);

        return ResponseEntity.ok(new ApiResponse<>(true, "Problems fetched successfully", response));
    }

    /** Returns the current user's DSA problems filtered by difficulty. */
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getProblemsByDifficulty(
            @PathVariable ProblemDifficulty difficulty) {

        List<DSAProblemResponseDto> response = dsaProblemService.getProblemsByDifficulty(difficulty);

        return ResponseEntity.ok(new ApiResponse<>(true, "Problems fetched successfully", response));
    }

    /** Searches the current user's DSA problems by a title keyword. */
    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> searchByTitle(
            @RequestParam String keyword) {

        List<DSAProblemResponseDto> response = dsaProblemService.searchByTitle(keyword);

        return ResponseEntity.ok(new ApiResponse<>(true, "Search completed successfully", response));
    }

    /** Searches the current user's DSA problems by a topic keyword. */
    @GetMapping("/search/topic")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> searchByTopic(
            @RequestParam String keyword) {

        List<DSAProblemResponseDto> response = dsaProblemService.searchByTopic(keyword);

        return ResponseEntity.ok(new ApiResponse<>(true, "Search completed successfully", response));
    }
}
