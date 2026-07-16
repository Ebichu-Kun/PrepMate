package com.strawHat.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interview_sessions")
@Getter
@Setter
public class InterviewSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private String difficulty;

    private Integer score;

    private String feedback;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @JsonManagedReference
    @OneToMany(
            mappedBy = "interviewSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<InterviewQuestion> questions = new ArrayList<>();
}
