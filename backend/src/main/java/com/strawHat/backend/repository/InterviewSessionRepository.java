package com.strawHat.backend.repository;

import com.strawHat.backend.entity.InterviewSession;
import com.strawHat.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {

    List<InterviewSession> findByUser(User user);

    Optional<InterviewSession> findByIdAndUser(Long id, User user);
}