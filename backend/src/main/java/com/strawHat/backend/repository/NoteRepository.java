package com.strawHat.backend.repository;

import com.strawHat.backend.entity.Note;
import com.strawHat.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);

    Optional<Note> findByIdAndUser(Long id, User user);

    List<Note> findByTitleContainingIgnoreCaseAndUser(String title, User user);

}