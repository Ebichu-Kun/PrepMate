package com.strawHat.backend.service;

import com.strawHat.backend.dto.NoteRequestDto;
import com.strawHat.backend.dto.NoteResponseDto;

import java.io.IOException;
import java.util.List;

/** Business operations for managing a user's notes. */
public interface NoteService {

    /** Creates a new note owned by the current user. */
    NoteResponseDto createNote(NoteRequestDto request);

    /** Returns all notes owned by the current user. */
    List<NoteResponseDto> getAllNotes();

    /** Updates an existing note owned by the current user. */
    NoteResponseDto updateNote(Long id, NoteRequestDto request);

    /** Deletes a note owned by the current user. */
    void deleteNote(Long id) throws IOException;

    /** Returns a single note owned by the current user. */
    NoteResponseDto getNoteById(Long id);

    /** Searches the current user's notes by title keyword. */
    List<NoteResponseDto> searchNotesByTitle(String title);
}
