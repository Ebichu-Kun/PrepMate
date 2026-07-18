package com.strawHat.backend.repository;

import com.strawHat.backend.entity.DSAProblem;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DSAProblemRepository
        extends JpaRepository<DSAProblem, Long> {

    List<DSAProblem> findByUserOrderByCreatedAtDesc(User user);

    Optional<DSAProblem> findByIdAndUser(Long id, User user);

    List<DSAProblem> findByUserAndStatusOrderByCreatedAtDesc(
            User user,
            ProblemStatus status
    );

    List<DSAProblem> findByUserAndDifficultyOrderByCreatedAtDesc(
            User user,
            ProblemDifficulty difficulty
    );

    List<DSAProblem> findByUserAndTopicContainingIgnoreCaseOrderByCreatedAtDesc(
            User user,
            String topic
    );

    List<DSAProblem> findByUserAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
            User user,
            String title
    );
}