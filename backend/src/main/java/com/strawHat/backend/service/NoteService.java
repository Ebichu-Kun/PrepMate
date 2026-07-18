package com.strawHat.backend.service;

import com.strawHat.backend.dto.NoteRequestDto;
import com.strawHat.backend.dto.NoteResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto request);

    List<NoteResponseDto> getAllNotes();

    NoteResponseDto updateNote(Long id, NoteRequestDto request);

    void deleteNote(Long id) throws IOException;

    NoteResponseDto getNoteById(Long id);

    List<NoteResponseDto> searchNotesByTitle(String title);

    NoteResponseDto uploadNote(
            String title,
            String content
    );


}