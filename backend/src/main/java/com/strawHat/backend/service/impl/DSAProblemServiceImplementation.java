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

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new UserNotFoundException(
                    "Authenticated user not found"
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        ));
    }

    @Override
    public DSAProblemResponseDto createProblem(
            DSAProblemRequestDto request) {

        User currentUser = getCurrentUser();

        DSAProblem problem = new DSAProblem();

        updateEntity(problem, request, true);

        problem.setUser(currentUser);

        DSAProblem savedProblem =
                dsaProblemRepository.save(problem);

        return mapToResponseDto(savedProblem);
    }

    @Override
    public List<DSAProblemResponseDto> getAllProblems() {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public DSAProblemResponseDto getProblemById(Long id) {

        User currentUser = getCurrentUser();

        DSAProblem problem =
                findProblemByIdAndUser(id, currentUser);

        return mapToResponseDto(problem);
    }

    @Override
    public DSAProblemResponseDto updateProblem(
            Long id,
            DSAProblemRequestDto request) {

        User currentUser = getCurrentUser();

        DSAProblem problem =
                findProblemByIdAndUser(id, currentUser);

        updateEntity(problem, request, false);

        DSAProblem updatedProblem =
                dsaProblemRepository.save(problem);

        return mapToResponseDto(updatedProblem);
    }

    @Override
    public void deleteProblem(Long id) {

        User currentUser = getCurrentUser();

        DSAProblem problem =
                findProblemByIdAndUser(id, currentUser);

        dsaProblemRepository.delete(problem);
    }

    @Override
    public List<DSAProblemResponseDto> getProblemsByStatus(
            ProblemStatus status) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndStatusOrderByCreatedAtDesc(
                        currentUser,
                        status
                )
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<DSAProblemResponseDto> getProblemsByDifficulty(
            ProblemDifficulty difficulty) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndDifficultyOrderByCreatedAtDesc(
                        currentUser,
                        difficulty
                )
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<DSAProblemResponseDto> searchByTitle(
            String title) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                        currentUser,
                        title
                )
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<DSAProblemResponseDto> searchByTopic(
            String topic) {

        User currentUser = getCurrentUser();

        return dsaProblemRepository
                .findByUserAndTopicContainingIgnoreCaseOrderByCreatedAtDesc(
                        currentUser,
                        topic
                )
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private DSAProblem findProblemByIdAndUser(
            Long id,
            User user) {

        return dsaProblemRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new DSAProblemNotFoundException(
                                "DSA problem not found"
                        ));
    }

    private void updateEntity(
            DSAProblem problem,
            DSAProblemRequestDto request,
            boolean creating) {

        problem.setTitle(request.getTitle().trim());
        problem.setTopic(request.getTopic().trim());
        problem.setDifficulty(request.getDifficulty());

        if (request.getStatus() != null) {
            problem.setStatus(request.getStatus());
        } else if (creating) {
            problem.setStatus(ProblemStatus.NOT_STARTED);
        }

        problem.setPlatform(
                normalizeText(request.getPlatform())
        );

        problem.setProblemUrl(
                normalizeText(request.getProblemUrl())
        );

        problem.setNotes(
                normalizeText(request.getNotes())
        );
    }

    private String normalizeText(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private DSAProblemResponseDto mapToResponseDto(
            DSAProblem problem) {

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