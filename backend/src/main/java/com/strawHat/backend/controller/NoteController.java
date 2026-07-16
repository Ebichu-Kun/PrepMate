package com.strawHat.backend.controller;

import com.strawHat.backend.dto.ApiResponse;
import com.strawHat.backend.dto.NoteRequestDto;
import com.strawHat.backend.dto.NoteResponseDto;
import com.strawHat.backend.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDto>> createNote(
            @Valid @RequestBody NoteRequestDto request) {

        NoteResponseDto response = noteService.createNote(request);

        ApiResponse<NoteResponseDto> apiResponse =
                new ApiResponse<>(
                        true,
                        "Note created successfully",
                        response
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> getAllNotes() {

        List<NoteResponseDto> notes = noteService.getAllNotes();

        ApiResponse<List<NoteResponseDto>> response =
                new ApiResponse<>(
                        true,
                        "Notes fetched successfully",
                        notes
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto request) {

        NoteResponseDto response = noteService.updateNote(id, request);

        ApiResponse<NoteResponseDto> apiResponse =
                new ApiResponse<>(
                        true,
                        "Note updated successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long id) throws IOException {

        noteService.deleteNote(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Note deleted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> getNoteById(
            @PathVariable Long id) {

        NoteResponseDto note = noteService.getNoteById(id);

        ApiResponse<NoteResponseDto> response =
                new ApiResponse<>(
                        true,
                        "Note fetched successfully",
                        note
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> searchNotesByTitle(
            @RequestParam String title) {

        List<NoteResponseDto> notes =
                noteService.searchNotesByTitle(title);

        ApiResponse<List<NoteResponseDto>> response =
                new ApiResponse<>(
                        true,
                        "Notes fetched successfully",
                        notes
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<NoteResponseDto>> uploadNote(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile file) throws IOException {

        NoteResponseDto response =
                noteService.uploadNote(title, content, file);

        ApiResponse<NoteResponseDto> apiResponse =
                new ApiResponse<>(
                        true,
                        "Note uploaded successfully",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadNote(
            @PathVariable Long id) throws MalformedURLException {

        Resource resource = noteService.downloadNote(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                resource.getFilename() +
                                "\""
                )
                .body(resource);
    }
}
