package com.strawHat.backend.dto;

import lombok.Getter;
import lombok.Setter;

/** Request body for creating or updating a note. */
@Getter
@Setter
public class NoteRequestDto {
    private String title;
    private String content;
}
