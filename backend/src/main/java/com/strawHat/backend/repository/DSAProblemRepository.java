package com.strawHat.backend.repository;

import com.strawHat.backend.entity.DSAProblem;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Data access for {@link DSAProblem} entities, scoped per-user.
 */
public interface DSAProblemRepository
        extends JpaRepository<DSAProblem, Long> {

    /** Returns all problems owned by the given user, newest first. */
    List<DSAProblem> findByUserOrderByCreatedAtDesc(User user);

    /** Finds a single problem by id, but only if it belongs to the given user. */
    Optional<DSAProblem> findByIdAndUser(Long id, User user);

    /** Returns the user's problems filtered by status, newest first. */
    List<DSAProblem> findByUserAndStatusOrderByCreatedAtDesc(
            User user,
            ProblemStatus status
    );

    /** Returns the user's problems filtered by difficulty, newest first. */
    List<DSAProblem> findByUserAndDifficultyOrderByCreatedAtDesc(
            User user,
            ProblemDifficulty difficulty
    );

    /** Searches the user's problems by topic (case-insensitive, partial match), newest first. */
    List<DSAProblem> findByUserAndTopicContainingIgnoreCaseOrderByCreatedAtDesc(
            User user,
            String topic
    );

    /** Searches the user's problems by title (case-insensitive, partial match), newest first. */
    List<DSAProblem> findByUserAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
            User user,
            String title
    );
}
