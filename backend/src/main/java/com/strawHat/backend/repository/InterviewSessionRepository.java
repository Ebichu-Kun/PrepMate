package com.strawHat.backend.repository;

import com.strawHat.backend.entity.InterviewSession;
import com.strawHat.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access for {@link InterviewSession} entities, scoped per-user.
 */
@Repository
public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {

    /** Returns all interview sessions owned by the given user. */
    List<InterviewSession> findByUser(User user);

    /** Finds a single session by id, but only if it belongs to the given user. */
    Optional<InterviewSession> findByIdAndUser(Long id, User user);
}
