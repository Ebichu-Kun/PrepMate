package com.strawHat.backend.repository;

import com.strawHat.backend.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for {@link InterviewQuestion} entities.
 */
@Repository
public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    /** Returns all questions for a session, ordered by their position in the interview. */
    List<InterviewQuestion> findByInterviewSessionIdOrderByQuestionOrderAsc(Long interviewSessionId);
}
