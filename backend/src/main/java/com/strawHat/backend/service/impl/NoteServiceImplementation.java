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

@Service
public class NoteServiceImplementation implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImplementation(NoteRepository noteRepository,
                                     UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NoteResponseDto createNote(NoteRequestDto request) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        NoteResponseDto response = new NoteResponseDto();

        response.setId(savedNote.getId());
        response.setTitle(savedNote.getTitle());
        response.setContent(savedNote.getContent());
        response.setCreatedAt(savedNote.getCreatedAt());
        response.setUpdatedAt(savedNote.getUpdatedAt());

        return response;
    }

    @Override
    public List<NoteResponseDto> getAllNotes() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Note> notes = noteRepository.findByUser(user);

        return notes.stream()
                .map(note -> {
                    NoteResponseDto response = new NoteResponseDto();

                    response.setId(note.getId());
                    response.setTitle(note.getTitle());
                    response.setContent(note.getContent());
                    response.setCreatedAt(note.getCreatedAt());
                    response.setUpdatedAt(note.getUpdatedAt());

                    return response;
                })
                .toList();
    }

    @Override
    public NoteResponseDto updateNote(Long id, NoteRequestDto request) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException(
                    "You are not allowed to update this note"
            );
        }
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note updatedNote = noteRepository.save(note);

        NoteResponseDto response = new NoteResponseDto();

        response.setId(updatedNote.getId());
        response.setTitle(updatedNote.getTitle());
        response.setContent(updatedNote.getContent());
        response.setCreatedAt(updatedNote.getCreatedAt());
        response.setUpdatedAt(updatedNote.getUpdatedAt());

        return response;
    }

    @Override
    public void deleteNote(Long id) throws IOException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException(
                    "You are not allowed to delete this note"
            );
        }

        noteRepository.delete(note);
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public NoteResponseDto getNoteById(Long id) {
        User currentUser = getCurrentUser();
        Note note = noteRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new NoteNotFoundException("Note not found"));
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(note.getId());
        dto.setContent(note.getContent());
        dto.setTitle(note.getTitle());
        dto.setUpdatedAt(note.getUpdatedAt());
        dto.setCreatedAt(note.getCreatedAt());

        return dto;
    }

    @Override
    public List<NoteResponseDto> searchNotesByTitle(String title) {
        User currentUser = getCurrentUser();

        List<Note> notes =
                noteRepository.findByTitleContainingIgnoreCaseAndUser(
                        title,
                        currentUser
                );

        return notes.stream().map(note -> new NoteResponseDto(
                note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt() , note.getUpdatedAt()
        )).toList();
    }

    @Override
    public NoteResponseDto uploadNote(
            String title,
            String content
            ) {

        User currentUser = getCurrentUser();


        Note note = new Note();

        note.setTitle(title);
        note.setContent(content);
        note.setUser(currentUser);



        Note savedNote = noteRepository.save(note);

        NoteResponseDto response = new NoteResponseDto();

        response.setId(savedNote.getId());
        response.setTitle(savedNote.getTitle());
        response.setContent(savedNote.getContent());
        response.setCreatedAt(savedNote.getCreatedAt());
        response.setUpdatedAt(savedNote.getUpdatedAt());
        return response;
    }

}
