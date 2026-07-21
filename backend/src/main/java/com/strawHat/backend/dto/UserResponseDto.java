package com.strawHat.backend.dto;

import lombok.Getter;
import lombok.Setter;

/** Public-facing representation of a user (never includes the password). */
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
}
