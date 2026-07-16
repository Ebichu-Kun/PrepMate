package com.strawHat.backend.repository;

import com.strawHat.backend.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {
    List<InterviewQuestion> findByInterviewSessionIdOrderByQuestionOrderAsc(Long interviewSessionId);
    Optional<InterviewQuestion> findByIdAndInterviewSessionId(
            Long questionId,
            Long interviewSessionId
    );
}