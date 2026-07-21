package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.NoteRequestDto;
import com.strawHat.backend.dto.NoteResponseDto;
import com.strawHat.backend.entity.Note;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.exception.NoteNotFoundException;
import com.strawHat.backend.exception.UnauthorizedAccessException;
import com.strawHat.backend.exception.UserNotFoundException;
import com.strawHat.backend.repository.NoteRepository;
import com.strawHat.backend.repository.UserRepository;
import com.strawHat.backend.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Handles CRUD and search operations for notes, always scoping access to
 * the currently authenticated user.
 */
@Service
public class NoteServiceImplementation implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImplementation(NoteRepository noteRepository,
                                      UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    /** Resolves the currently authenticated user from the security context. */
    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /** Creates a new note owned by the current user. */
    @Override
    public NoteResponseDto createNote(NoteRequestDto request) {

        User user = getCurrentUser();

        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        return mapToResponseDto(savedNote);
    }

    /** Returns all notes owned by the current user. */
    @Override
    public List<NoteResponseDto> getAllNotes() {

        User user = getCurrentUser();

        List<Note> notes = noteRepository.findByUser(user);

        return notes.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Updates an existing note, provided it belongs to the current user. */
    @Override
    public NoteResponseDto updateNote(Long id, NoteRequestDto request) {

        User user = getCurrentUser();

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to update this note");
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updatedNote = noteRepository.save(note);

        return mapToResponseDto(updatedNote);
    }

    /** Deletes a note, provided it belongs to the current user. */
    @Override
    public void deleteNote(Long id) throws IOException {

        User user = getCurrentUser();

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to delete this note");
        }

        noteRepository.delete(note);
    }

    /** Returns a single note, provided it belongs to the current user. */
    @Override
    public NoteResponseDto getNoteById(Long id) {

        User currentUser = getCurrentUser();

        Note note = noteRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        return mapToResponseDto(note);
    }

    /** Searches the current user's notes by title keyword (case-insensitive). */
    @Override
    public List<NoteResponseDto> searchNotesByTitle(String title) {

        User currentUser = getCurrentUser();

        List<Note> notes =
                noteRepository.findByTitleContainingIgnoreCaseAndUser(title, currentUser);

        return notes.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /** Maps a Note entity to its response DTO. */
    private NoteResponseDto mapToResponseDto(Note note) {
        return new NoteResponseDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}
