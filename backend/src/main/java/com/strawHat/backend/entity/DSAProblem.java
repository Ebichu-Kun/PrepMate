package com.strawHat.backend.entity;

import com.strawHat.backend.enums.ProblemDifficulty;
import com.strawHat.backend.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a single DSA  practice problem
 * tracked by a user, including its difficulty, solve status, and notes.
 */
@Entity
@Table(name = "dsa_problems")
@Getter
@Setter
@NoArgsConstructor
public class DSAProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status = ProblemStatus.NOT_STARTED;

    private String platform;

    private String problemUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime solvedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Sets the creation timestamp and default status/solvedAt values before
     * the entity is first persisted.
     */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = ProblemStatus.NOT_STARTED;
        }

        if (status == ProblemStatus.SOLVED && solvedAt == null) {
            solvedAt = LocalDateTime.now();
        }
    }

    /**
     * Keeps solvedAt in sync with status on every update: sets it when the
     * problem becomes SOLVED, and clears it otherwise.
     */
    @PreUpdate
    public void preUpdate() {
        if (status == ProblemStatus.SOLVED && solvedAt == null) {
            solvedAt = LocalDateTime.now();
        }

        if (status != ProblemStatus.SOLVED) {
            solvedAt = null;
        }
    }
}
