package com.strawHat.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {
    private String title;
    private String content;
}
