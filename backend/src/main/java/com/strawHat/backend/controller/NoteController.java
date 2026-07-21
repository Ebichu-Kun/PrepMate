package com.strawHat.backend.controller;

import com.strawHat.backend.dto.ApiResponse;
import com.strawHat.backend.dto.NoteRequestDto;
import com.strawHat.backend.dto.NoteResponseDto;
import com.strawHat.backend.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/** Manages the authenticated user's notes. */
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /** Creates a new note for the current user. */
    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDto>> createNote(
            @Valid @RequestBody NoteRequestDto request) {

        NoteResponseDto response = noteService.createNote(request);

        ApiResponse<NoteResponseDto> apiResponse =
                new ApiResponse<>(true, "Note created successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /** Returns all notes belonging to the current user. */
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> getAllNotes() {

        List<NoteResponseDto> notes = noteService.getAllNotes();

        ApiResponse<List<NoteResponseDto>> response =
                new ApiResponse<>(true, "Notes fetched successfully", notes);

        return ResponseEntity.ok(response);
    }

    /** Updates an existing note by id. */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto request) {

        NoteResponseDto response = noteService.updateNote(id, request);

        ApiResponse<NoteResponseDto> apiResponse =
                new ApiResponse<>(true, "Note updated successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    /** Deletes a note by id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long id) throws IOException {

        noteService.deleteNote(id);

        ApiResponse<Void> response =
                new ApiResponse<>(true, "Note deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    /** Returns a single note by id. */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> getNoteById(@PathVariable Long id) {

        NoteResponseDto note = noteService.getNoteById(id);

        ApiResponse<NoteResponseDto> response =
                new ApiResponse<>(true, "Note fetched successfully", note);

        return ResponseEntity.ok(response);
    }

    /** Searches the current user's notes by a title keyword. */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> searchNotesByTitle(
            @RequestParam String title) {

        List<NoteResponseDto> notes = noteService.searchNotesByTitle(title);

        ApiResponse<List<NoteResponseDto>> response =
                new ApiResponse<>(true, "Notes fetched successfully", notes);

        return ResponseEntity.ok(response);
    }
}
