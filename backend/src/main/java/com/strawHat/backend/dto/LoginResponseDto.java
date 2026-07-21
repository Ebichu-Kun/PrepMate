package com.strawHat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Response body returned after a successful login, containing the JWT and user info. */
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {

    private String token;
    private UserResponseDto user;
}
