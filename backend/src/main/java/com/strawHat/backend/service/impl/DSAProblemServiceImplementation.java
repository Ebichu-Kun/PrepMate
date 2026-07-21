package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.DSAProblemRequestDto;
import com.strawHat.backend.dto.DSAProblemResponseDto;
import com.strawHat.backend.entity.DSAProblem;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import com.strawHat.backend.exception.DSAProblemNotFoundException;
import com.strawHat.backend.exception.UserNotFoundException;
import com.strawHat.backend.repository.DSAProblemRepository;
import com.strawHat.backend.repository.UserRepository;
import com.strawHat.backend.service.DSAProblemService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles CRUD and search operations for DSA problems, always scoping
 * access to the currently authenticated user.
 */
@Service
public class DSAProblemServiceImplementation implements DSAProblemService {

    private final DSAProblemRepository dsaProblemRepository;
    private final UserRepository userRepository;

    public DSAProblemServiceImplementation(
            DSAProblemRepository dsaProblemRepository,
            UserRepository userRepository) {

        this.dsaProblemRepository = dsaProblemRepository;
        this.userRepository = userRepository;
    }

    /** Resolves the currently authenticated user from the security context. */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException("Authenticated user not found");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /** Creates a new DSA problem for the current user. */
    @Override
    public DSAProblemResponseDto createProblem(DSAProblemRequestDto request) {

        User currentUser = getCurrentUser();

        DSAProblem problem = new DSAProblem();

        updateEntity(problem, request, true);

        problem.setUser(currentUser);

        DSAProblem savedProblem = dsaProblemRepository.save(problem);

        return mapToResponseDto(savedProblem);
    }

    /** Returns all DSA problems belonging to the current user, newest first. */
    @Override
    public List<DSAProblemResponseDto> getAllProblems() {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Returns a single DSA problem, provided it belongs to the current user. */
    @Override
    public DSAProblemResponseDto getProblemById(Long id) {

        User currentUser = getCurrentUser();

        DSAProblem problem = findProblemByIdAndUser(id, currentUser);

        return mapToResponseDto(problem);
    }

    /** Updates an existing DSA problem, provided it belongs to the current user. */
    @Override
    public DSAProblemResponseDto updateProblem(Long id, DSAProblemRequestDto request) {

        User currentUser = getCurrentUser();

        DSAProblem problem = findProblemByIdAndUser(id, currentUser);

        updateEntity(problem, request, false);

        DSAProblem updatedProblem = dsaProblemRepository.save(problem);

        return mapToResponseDto(updatedProblem);
    }

    /** Deletes a DSA problem, provided it belongs to the current user. */
    @Override
    public void deleteProblem(Long id) {

        User currentUser = getCurrentUser();

        DSAProblem problem = findProblemByIdAndUser(id, currentUser);

        dsaProblemRepository.delete(problem);
    }

    /** Returns the current user's DSA problems filtered by status, newest first. */
    @Override
    public List<DSAProblemResponseDto> getProblemsByStatus(ProblemStatus status) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndStatusOrderByCreatedAtDesc(currentUser, status)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Returns the current user's DSA problems filtered by difficulty, newest first. */
    @Override
    public List<DSAProblemResponseDto> getProblemsByDifficulty(ProblemDifficulty difficulty) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndDifficultyOrderByCreatedAtDesc(currentUser, difficulty)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Searches the current user's DSA problems by title keyword, newest first. */
    @Override
    public List<DSAProblemResponseDto> searchByTitle(String title) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(currentUser, title)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Searches the current user's DSA problems by topic keyword, newest first. */
    @Override
    public List<DSAProblemResponseDto> searchByTopic(String topic) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndTopicContainingIgnoreCaseOrderByCreatedAtDesc(currentUser, topic)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Finds a DSA problem by id, throwing if it doesn't exist or isn't owned by the user. */
    private DSAProblem findProblemByIdAndUser(Long id, User user) {

        return dsaProblemRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new DSAProblemNotFoundException("DSA problem not found"));
    }

    /** Copies request fields onto the entity, applying defaults when creating a new problem. */
    private void updateEntity(DSAProblem problem, DSAProblemRequestDto request, boolean creating) {

        problem.setTitle(request.getTitle().trim());
        problem.setTopic(request.getTopic().trim());
        problem.setDifficulty(request.getDifficulty());

        if (request.getStatus() != null) {
            problem.setStatus(request.getStatus());
        } else if (creating) {
            problem.setStatus(ProblemStatus.NOT_STARTED);
        }

        problem.setPlatform(normalizeText(request.getPlatform()));
        problem.setProblemUrl(normalizeText(request.getProblemUrl()));
        problem.setNotes(normalizeText(request.getNotes()));
    }

    /** Trims a string, converting blank input to null so optional fields stay unset. */
    private String normalizeText(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    /** Maps a DSAProblem entity to its response DTO. */
    private DSAProblemResponseDto mapToResponseDto(DSAProblem problem) {

        return new DSAProblemResponseDto(
                problem.getId(),
                problem.getTitle(),
                problem.getTopic(),
                problem.getDifficulty(),
                problem.getStatus(),
                problem.getPlatform(),
                problem.getProblemUrl(),
                problem.getNotes(),
                problem.getSolvedAt(),
                problem.getCreatedAt()
        );
    }
}
