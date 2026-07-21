package com.strawHat.backend.repository;

import com.strawHat.backend.entity.Note;
import com.strawHat.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access for {@link Note} entities, scoped per-user.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /** Returns all notes owned by the given user. */
    List<Note> findByUser(User user);

    /** Finds a single note by id, but only if it belongs to the given user. */
    Optional<Note> findByIdAndUser(Long id, User user);

    /** Searches the user's notes by title (case-insensitive, partial match). */
    List<Note> findByTitleContainingIgnoreCaseAndUser(String title, User user);
}
