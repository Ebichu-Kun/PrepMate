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

@RestController
@RequestMapping("/api/dsa")
public class DSAProblemController {

    private final DSAProblemService dsaProblemService;

    public DSAProblemController(
            DSAProblemService dsaProblemService) {

        this.dsaProblemService = dsaProblemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> createProblem(
            @Valid @RequestBody DSAProblemRequestDto request) {

        DSAProblemResponseDto response =
                dsaProblemService.createProblem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "DSA problem created successfully",
                        response
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getAllProblems() {

        List<DSAProblemResponseDto> response =
                dsaProblemService.getAllProblems();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problems fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> getProblemById(
            @PathVariable Long id) {

        DSAProblemResponseDto response =
                dsaProblemService.getProblemById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DSAProblemResponseDto>> updateProblem(
            @PathVariable Long id,
            @Valid @RequestBody DSAProblemRequestDto request) {

        DSAProblemResponseDto response =
                dsaProblemService.updateProblem(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProblem(
            @PathVariable Long id) {

        dsaProblemService.deleteProblem(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem deleted successfully",
                        "Deleted"
                )
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getProblemsByStatus(
            @PathVariable ProblemStatus status) {

        List<DSAProblemResponseDto> response =
                dsaProblemService.getProblemsByStatus(status);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problems fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> getProblemsByDifficulty(
            @PathVariable ProblemDifficulty difficulty) {

        List<DSAProblemResponseDto> response =
                dsaProblemService.getProblemsByDifficulty(difficulty);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problems fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> searchByTitle(
            @RequestParam String keyword) {

        List<DSAProblemResponseDto> response =
                dsaProblemService.searchByTitle(keyword);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Search completed successfully",
                        response
                )
        );
    }

    @GetMapping("/search/topic")
    public ResponseEntity<ApiResponse<List<DSAProblemResponseDto>>> searchByTopic(
            @RequestParam String keyword) {

        List<DSAProblemResponseDto> response =
                dsaProblemService.searchByTopic(keyword);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Search completed successfully",
                        response
                )
        );
    }
}